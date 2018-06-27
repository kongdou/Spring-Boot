package fun.deepsky.springboot.parallel;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
// 开启批处理
public class ReaderConfig {

	@Bean
	// 产生一个Bean交给Spring管理
	public ItemReader<Vehicle> jdbcPagingItemReader(DataSource dataSource) {

		SqlPagingQueryProviderFactoryBean bean = new SqlPagingQueryProviderFactoryBean();
		bean.setDataSource(dataSource);
		bean.setSelectClause("*");
		bean.setFromClause("t_c_vehicle_msg");
		bean.setSortKey("vehicle_id");

		JdbcPagingItemReader<Vehicle> reader = new JdbcPagingItemReader<Vehicle>();
		// 设置数据源
		reader.setDataSource(dataSource);
		try {
			reader.setQueryProvider(bean.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RowMapper<Vehicle> rowMapper = new RowMapper<Vehicle>() {
			@Override
			public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Vehicle vehicle = new Vehicle();
				vehicle.setVehicle_id(rs.getString("vehicle_id"));
				vehicle.setVehicle_name(rs.getString("vehicle_name"));
				
				vehicle.setSearch_code(rs.getString("search_code"));
				vehicle.setVehicle_maker(rs.getString("vehicle_makder"));
				vehicle.setPrice_t(rs.getDouble("price_t"));
				return vehicle;
			}
		};
		reader.setRowMapper(rowMapper);

		return reader;
	}

	@Bean
	public ItemProcessor<Vehicle, Vehicle> processor() {
		DbItemProcessor processor = new DbItemProcessor();
		processor.setValidator(dbBeanValidator());
		return processor;
	}

	@Bean
	public ItemWriter<Vehicle> writer(DataSource dataSource) {
		JdbcBatchItemWriter<Vehicle> writer = new JdbcBatchItemWriter<Vehicle>();

		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Vehicle>());

		// String sql = "insert into Vehicle_new(name,age,nation,address)
		// values(:name,:age,:nation,:address)";
		//String sql = "update Vehicle set age = :age where name=:name";
		String sql = "update t_c_vehicle_msg set price_t= :price_t where vehicle_id = :vehicle_id";
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
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Vehicle> reader, ItemWriter<Vehicle> writer,
			ItemProcessor<Vehicle, Vehicle> processor) {
		return stepBuilderFactory.get("step1").<Vehicle, Vehicle>chunk(100).reader(reader).processor(processor)
				.writer(writer)
				.taskExecutor(new SimpleAsyncTaskExecutor())
				.throttleLimit(5)
				.build();
	}

	@Bean
	public Validator<Vehicle> dbBeanValidator() {
		return new DbBeanValidator<>();
	}

	@Bean
	public DbJobListener dbJobListener() {
		return new DbJobListener();
	}
}
