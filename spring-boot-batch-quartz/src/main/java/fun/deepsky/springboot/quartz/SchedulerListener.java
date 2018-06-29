package fun.deepsky.springboot.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
    public JobScheduler scheduler;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		try {
			scheduler.scheduleJobs();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	   @Bean
	    public SchedulerFactoryBean schedulerFactoryBean(){
	        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
	        return schedulerFactoryBean;
	    }
}
