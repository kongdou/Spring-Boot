package fun.zxj.springboot.scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
		DemoSingletonService ds1 = context.getBean(DemoSingletonService.class);
		DemoSingletonService ds2 = context.getBean(DemoSingletonService.class);
		
		DemoPropertypeService dp1 = context.getBean(DemoPropertypeService.class);
		DemoPropertypeService dp2 = context.getBean(DemoPropertypeService.class);
		
		System.out.println("ds1 = ds2 ? "+(ds1==ds2)); //结果为true
		System.out.println("dp1 = dp2 ? "+(dp1==dp2)); //结果为false
		
		context.close();
	}

}
