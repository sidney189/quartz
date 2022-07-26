package com.example.task;

import com.example.task.job.Job1;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;


    @GetMapping("/test")
    public void startJob() throws SchedulerException {
        this.scheduler.start();
        final JobDetail jobDetail = JobBuilder.newJob(Job1.class).withIdentity("job1", "group1").build();
        final CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 52 18 * *  ?");
        final CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").withSchedule(scheduleBuilder).build();
        this.scheduler.scheduleJob(jobDetail, trigger);
    }
}
