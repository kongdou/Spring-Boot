package fun.zxj.springboot.prepost;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrePostConfig.class);
		
		BeanWayService beanWayService = ac.getBean(BeanWayService.class);
		
		JSR250WayService jsr250WayService = ac.getBean(JSR250WayService.class);
		
		ac.close();
		
	}
}
