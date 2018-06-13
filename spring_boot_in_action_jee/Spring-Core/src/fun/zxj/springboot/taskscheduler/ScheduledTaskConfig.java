package fun.zxj.springboot.taskscheduler;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configurable
@ComponentScan("fun.zxj.springboot.taskscheduler")
@EnableScheduling
public class ScheduledTaskConfig {

}
