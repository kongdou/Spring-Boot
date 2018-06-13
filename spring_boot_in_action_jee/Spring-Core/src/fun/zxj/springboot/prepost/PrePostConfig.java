package fun.zxj.springboot.prepost;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("fun.zxj.springboot.prepost")
public class PrePostConfig {

	@Bean(initMethod="init",destroyMethod="destory")
	BeanWayService beaWayService(){
		return new BeanWayService();
	}
	
	@Bean
	JSR250WayService jsr250WayService(){
		return new JSR250WayService();
	}
	
}
