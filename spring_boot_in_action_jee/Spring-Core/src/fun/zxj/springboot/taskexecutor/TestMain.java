package fun.zxj.springboot.taskexecutor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
	
	
	AsyncTaskService ats = ac.getBean(AsyncTaskService.class);
	
	for(int i=0;i<10;i++){
		ats.exeAsyncTask(i);
		ats.executeAsyncTaskPlus(i);
	}
	
	ac.close();
	
	}
	

}
