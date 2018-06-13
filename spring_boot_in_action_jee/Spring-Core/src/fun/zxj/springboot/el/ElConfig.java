package fun.zxj.springboot.el;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan("fun.zxj.springboot.el")
@PropertySource("classpath:fun/zxj/springboot/el/el.properties")
public class ElConfig {

	@Value("Hello World")
	private String normal;//注入普通字符
	
	@Value("#{systemProperties['os.name']}")
	private String osName;//注入操作系统属性
	
	@Value("#{T(java.lang.Math).random() * 100 }")
	private String randonNumber;//注入表达式结果
	
	@Value("#{demoService.name}")
	private String fromAnother;//注入其他Bean属性
	
	@Value("classpath:fun/zxj/springboot/el/el.txt")
	private Resource testFile;//注入文件资源
	
	@Value("http://www.baidu.com")
	private Resource testUrl;//注入网址资源

	@Value("${name}")
	private String bookName;//注入配置文件
	
	@Autowired
	private Environment environment;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure(){
		return new PropertySourcesPlaceholderConfigurer();
	}

	public void outputResource(){
		try {
		System.out.println(fromAnother);
		System.out.println(normal);
		System.out.println(osName);
		System.out.println(randonNumber);
		System.out.println(IOUtils.toString(testFile.getInputStream()));
		System.out.println(IOUtils.toString(testUrl.getInputStream()));
		System.out.println(bookName);
		System.out.println(environment.getProperty("age"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
