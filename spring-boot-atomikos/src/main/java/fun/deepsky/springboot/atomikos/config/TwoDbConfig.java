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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.mysql.cj.jdbc.MysqlXADataSource;

@Configuration
@MapperScan(basePackages = "fun.deepsky.springboot.atomikos.mapper.twodb",sqlSessionTemplateRef = "twoSqlSessionTemplate")
public class TwoDbConfig {

	@Autowired
	TwoDbProperties twoDbProperties;

	@Bean(name = "twoDataSource")
	public DataSource twoDataSource(TwoDbProperties twoDbProperties) throws SQLException {
		MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
		mysqlXADataSource.setUrl(twoDbProperties.getUrl());
		mysqlXADataSource.setUser(twoDbProperties.getUsername());
		mysqlXADataSource.setPassword(twoDbProperties.getPassword());

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXADataSource);
		xaDataSource.setUniqueResourceName("twoDataSource");

		xaDataSource.setMinPoolSize(twoDbProperties.getMinPoolSize());
		xaDataSource.setMaxPoolSize(twoDbProperties.getMaxPoolSize());
		xaDataSource.setMaxLifetime(twoDbProperties.getMaxLifetime());
		xaDataSource.setBorrowConnectionTimeout(twoDbProperties.getBorrowConnectionTimeout());
		xaDataSource.setLoginTimeout(twoDbProperties.getLoginTimeout());
		xaDataSource.setMaintenanceInterval(twoDbProperties.getMaintenanceInterval());
		xaDataSource.setMaxIdleTime(twoDbProperties.getMaxIdleTime());
		xaDataSource.setTestQuery(twoDbProperties.getTestQuery());
		return xaDataSource;
	}
	
	@Bean(name = "twoSqlSessionFactory")
	public SqlSessionFactory twoSqlSessionFactory(@Qualifier("twoDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/config/twodb/*.xml"));
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="twoSqlSessionTemplate")
	public SqlSessionTemplate twoSqlSessionTemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
