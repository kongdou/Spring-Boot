package fun.zxj.springboot.conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ConditionConfig.class);
		
		ListService ls = ac.getBean(ListService.class);
		
		System.out.println(ac.getEnvironment().getProperty("os.name")+"系统下的命令列表："+ls.showListCmd());
		
		ac.close();
	}

}
