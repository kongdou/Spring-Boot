package fun.deepsky.springboot.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import fun.deepsky.springboot.quartz.jobs.ScheduledFtpFetchCsvJob;

@Component
public class JobScheduler {

	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;

	/**
	 * 调度所有job
	 * @throws SchedulerException
	 */
	public void scheduleJobs() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		//定时获取CSV文件
		startFtpFetchCsvJob(scheduler);
		//定时查库
		//startJob2(scheduler);
	}

	private void startFtpFetchCsvJob(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduledFtpFetchCsvJob.class)
                .withIdentity("fetch_ftp_csv_job", "data").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("fetch_ftp_csv_tig", "data")
                                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
	}
}
