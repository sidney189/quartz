package com.example.task.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

    @Autowired
    private MyAdaptableJobFactory myAdaptableJobFactory;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 1、创建Job对象
     */
//    @Bean
//    public JobDetailFactoryBean jobDetailFactoryBean() {
//        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
//        //关联我们自己的Job类
//        factoryBean.setJobClass(QuartzDemoJob.class);  //QuartzDemoJob的实例化并没有经过Spring的处理，
//        // Spring的注入是要求注入的对象和被注入的对象都要在Spring的IOC容器中
//        return factoryBean;
//    }

    /**
     * 2、创建Trigger对象
     * Cron Trigger
     */
//    @Bean
//    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
//        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
//        //关联JobDetail对象
//        factoryBean.setJobDetail(jobDetailFactoryBean.getObject());
//        //设置触发时间
//        //factoryBean.setCronExpression("0/2 * * * * ?");  //每2秒触发一次， 分钟，小时，天，月，星期
//        factoryBean.setCronExpression("0 0-59 0-22 * * ?");  //在每天0-22点期间的每1分钟触发
//        return factoryBean;
//    }

    /**
     * 3、创建Scheduler
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();

        //factoryBean.setQuartzProperties(quartzProperties());
        factoryBean.setJobFactory(myAdaptableJobFactory);  //调用JobFactory把对象注入到SpringIOC容器中

        return factoryBean;
    }

    @Bean(name = "scheduler")
    public Scheduler scheduler() throws IOException{
        return this.schedulerFactoryBean().getScheduler();
    }

    public Properties quartzProperties() {

        final Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "jmwangQuartzScheduler");// 调度器的实例名
        prop.put("org.quartz.scheduler.instanceId", "AUTO");// 实例的标识
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");// 检查quartz是否有版本更新（true 不检查）
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");// 表名前缀
        prop.put("org.quartz.jobStore.isClustered", "false");// 集群开关
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");// 线程池的名字
        prop.put("org.quartz.threadPool.threadCount", "10");// 指定线程数量
        prop.put("org.quartz.threadPool.threadPriority", "5");// 线程优先级（1-10）默认为5
        prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        prop.put("org.quartz.dataSource.quartzDataSource.driver", "com.mysql.jdbc.Driver");
        prop.put("org.quartz.dataSource.quartzDataSource.URL", url);
        prop.put("org.quartz.dataSource.quartzDataSource.user", userName);
        prop.put("org.quartz.dataSource.quartzDataSource.password", password);
        prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "50");
        return prop;
    }

}
