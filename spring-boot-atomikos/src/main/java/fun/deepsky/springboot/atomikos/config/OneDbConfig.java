package fun.deepsky.springboot.atomikos.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.mysql.cj.jdbc.MysqlXADataSource;

@Configuration
@MapperScan(basePackages = "fun.deepsky.springboot.atomikos.mapper.onedb",sqlSessionTemplateRef = "oneSqlSessionTemplate")
public class OneDbConfig {

	@Autowired
	OneDbProperties oneDbProperties;

	@Primary
	@Bean(name = "oneDataSource")
	public DataSource oneDataSource(OneDbProperties oneDbProperties) throws SQLException {
		MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
		mysqlXADataSource.setUrl(oneDbProperties.getUrl());
		mysqlXADataSource.setUser(oneDbProperties.getUsername());
		mysqlXADataSource.setPassword(oneDbProperties.getPassword());

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXADataSource);
		xaDataSource.setUniqueResourceName("oneDataSource");

		xaDataSource.setMinPoolSize(oneDbProperties.getMinPoolSize());
		xaDataSource.setMaxPoolSize(oneDbProperties.getMaxPoolSize());
		xaDataSource.setMaxLifetime(oneDbProperties.getMaxLifetime());
		xaDataSource.setBorrowConnectionTimeout(oneDbProperties.getBorrowConnectionTimeout());
		xaDataSource.setLoginTimeout(oneDbProperties.getLoginTimeout());
		xaDataSource.setMaintenanceInterval(oneDbProperties.getMaintenanceInterval());
		xaDataSource.setMaxIdleTime(oneDbProperties.getMaxIdleTime());
		xaDataSource.setTestQuery(oneDbProperties.getTestQuery());
		System.out.println("1.........");
		return xaDataSource;
	}
	
	@Bean(name = "oneSqlSessionFactory")
	public SqlSessionFactory oneSqlSessionFactory(@Qualifier("oneDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/config/onedb/*.xml"));
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="oneSqlSessionTemplate")
	public SqlSessionTemplate oneSqlSessionTemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	
	
	
}
