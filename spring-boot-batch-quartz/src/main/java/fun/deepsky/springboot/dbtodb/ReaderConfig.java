package fun.deepsky.springboot.dbtodb;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.validator.Validator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import fun.deepsky.springboot.filetodb.domain.Person;

@Configuration
@EnableBatchProcessing
// 开启批处理
public class ReaderConfig {

	@Bean
	// 产生一个Bean交给Spring管理
	public ItemReader<Person> jdbcPagingItemReader(DataSource dataSource) {

		SqlPagingQueryProviderFactoryBean bean = new SqlPagingQueryProviderFactoryBean();
		bean.setDataSource(dataSource);
		bean.setSelectClause("*");
		bean.setFromClause("PERSON");
		bean.setSortKey("age");

		JdbcPagingItemReader<Person> reader = new JdbcPagingItemReader<Person>();
		// 设置数据源
		reader.setDataSource(dataSource);
		try {
			reader.setQueryProvider(bean.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     RowMapper<Person> rowMapper = new RowMapper<Person>() {
			@Override
			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Person person = new Person();
				person.setAddress(rs.getString("address"));
				person.setAge(rs.getInt("age"));
				person.setName(rs.getString("name"));
				person.setNation(rs.getString("nation"));
				return person;
			}
		};
	     reader.setRowMapper(rowMapper);
		
		return reader;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {
		DbItemProcessor processor = new DbItemProcessor();
		processor.setValidator(dbBeanValidator());
		return processor;
	}

	@Bean
	public ItemWriter<Person> writer(DataSource dataSource) {
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();

		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());

		//String sql = "insert into person_new(name,age,nation,address) values(:name,:age,:nation,:address)";
		String sql = "update person set age = :age where name=:name";
		writer.setSql(sql);
		writer.setDataSource(dataSource);

		return writer;
	}

	@Bean
	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType("mysql");
		return jobRepositoryFactoryBean.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager platformTransactionManager)
			throws Exception {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();

		simpleJobLauncher.setJobRepository(jobRepository(dataSource, platformTransactionManager));
		return simpleJobLauncher;
	}

	@Bean
	public Job importJob(JobBuilderFactory jobs, Step s1) {
		return jobs.get("importJob").incrementer(new RunIdIncrementer()).flow(s1).end().listener(dbJobListener())
				.build();
	}

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader, ItemWriter<Person> writer,
			ItemProcessor<Person, Person> processor) {
		return stepBuilderFactory.get("step1").<Person,Person>chunk(1).reader(reader).processor(processor).writer(writer).build();

	}

	@Bean
	public Validator<Person> dbBeanValidator() {
		return new DbBeanValidator<>();
	}

	@Bean
	public DbJobListener dbJobListener() {
		return new DbJobListener();
	}
}
