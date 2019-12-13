package org.yzkx.secin.core.quartz;

import java.io.IOException;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private JobFactory jobFactory;
	
	@Autowired
	@Qualifier("quartzDataSource")
	private DataSource dataSource;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("任务已启动: " + event.getSource());
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setQuartzProperties(propertiesFactoryBean.getObject());
		factory.setDataSource(this.dataSource);
		factory.setJobFactory(this.jobFactory);
		factory.setWaitForJobsToCompleteOnShutdown(true);
		factory.setOverwriteExistingJobs(false);
		factory.setStartupDelay(1);
		
		return factory;
	}
	
	@Bean("scheduler")
	public Scheduler scheduler() throws IOException {
		return schedulerFactoryBean().getScheduler();
	}
	
	@Bean
	public QuartzInitializerListener executorListener() {
		return new QuartzInitializerListener();
	}

}
