package fun.zxj.springboot.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionConfig {

	@Bean
	@Conditional(WindowCondition.class)
	public ListService windowListService(){
		return new WindowsListService();
	}
	
	@Bean
	@Conditional(MacCondition.class)
	public ListService macListService(){
		return new MacListService();
	}
	
}
