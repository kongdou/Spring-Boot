package fun.zxj.springboot.taskscheduler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ScheduledTaskConfig.class);
		
	}
}
