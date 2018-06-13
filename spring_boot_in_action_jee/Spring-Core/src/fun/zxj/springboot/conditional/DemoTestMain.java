package fun.zxj.springboot.conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DemoTestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DemoConfig.class);
		
		DemoService ds = ac.getBean(DemoService.class);
		
		ds.outputResult();
		
		ac.close();
	}

}
