package fun.deepsky.springboot.atomikos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "mysql.datasource.one")
public class OneDbProperties {

	private String url;
	private String username;
	private String password;
	/** min-pool-size 最小连接数 **/
	private int minPoolSize;
	/** max-pool-size 最大连接数 **/
	private int maxPoolSize;
	/** max-lifetime 连接最大存活时间 **/
	private int maxLifetime;
	/** borrow-connection-timeout 获取连接失败重新获等待最大时间，在这个时间内如果有可用连接，将返回 **/
	private int borrowConnectionTimeout;
	/** login-timeout java数据库连接池，最大可等待获取datasouce的时间 **/
	private int loginTimeout;
	/** maintenance-interval 连接回收时间 **/
	private int maintenanceInterval;
	/** max-idle-time 最大闲置时间，超过最小连接池连接的连接将将关闭 **/
	private int maxIdleTime;
	/** test-query 测试SQL **/
	private String testQuery;
	
}
