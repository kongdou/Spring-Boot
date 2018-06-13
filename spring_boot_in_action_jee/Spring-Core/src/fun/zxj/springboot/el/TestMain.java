package fun.zxj.springboot.el;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ElConfig.class);
		ElConfig elConfig = ac.getBean(ElConfig.class);
		
		elConfig.outputResource();
		
		ac.close();
	}
}
