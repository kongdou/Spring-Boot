package fun.zxj.springboot.mvc.first;

import java.util.List;

import messageconverter.MessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import fun.zxj.springboot.interceptor.DemoInterceptor;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan("fun.zxj.springboot.mvc.first")
public class MyMvcConfig extends WebMvcConfigurerAdapter{

	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/classes/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
	
	@Bean
	public DemoInterceptor demoIntercetor(){
		return new DemoInterceptor();
	}
	
	@Bean
	public MessageConverter converter(){
		return new MessageConverter();
	}
	/**
	 * 静态的js文件，直接获取
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
	}
	
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(demoIntercetor());
	}
	
	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController("/converter").setViewName("/converter");
		registry.addViewController("/sse").setViewName("/sse");
		registry.addViewController("/async").setViewName("/async");
	}
	
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
		converters.add(converter());
	}

}
