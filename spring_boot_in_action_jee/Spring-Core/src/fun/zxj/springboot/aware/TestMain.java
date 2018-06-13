package fun.zxj.springboot.aware;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AwareConfig.class);
		
		AwareService as = ac.getBean(AwareService.class);
		
		as.outputResult();
		
		ac.close();
	}

}
