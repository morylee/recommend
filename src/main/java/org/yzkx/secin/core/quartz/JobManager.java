package org.yzkx.secin.core.quartz;

import java.util.Date;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class JobManager {

	@Autowired
	@Qualifier("scheduler")
	private Scheduler scheduler;
	
	/**
	 * 创建日期任务
	 * @param jobInfo
	 * @param uniqueCode
	 * @param start
	 * @param params
	 */
	public void addJob(JobInfo jobInfo, String uniqueCode, Date start, Map<String, Object> params) {
		addJob(jobInfo, uniqueCode, start, null, null, null, params);
	}
	
	/**
	 * 创建日期任务
	 * @param jobInfo
	 * @param uniqueCode
	 * @param start
	 * @param end
	 * @param repeatCount
	 * @param repeatInterval
	 * @param params
	 */
	public void addJob(JobInfo jobInfo, String uniqueCode, Date start, Date end, Integer repeatCount, Integer repeatInterval, Map<String, Object> params) {
		if (isValidExpression(start)) {
			String jobName = jobName(jobInfo.getName(), uniqueCode);
			
			JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.setName(jobName);
			jobDetail.setGroup(jobInfo.getGroupName());
			jobDetail.setJobClass(jobInfo.getClazz());
			jobDetail.setDurability(true);
			if (params != null && !params.isEmpty()) jobDetail.setJobDataMap(new JobDataMap(params));
			
			TriggerKey triggerKey = new TriggerKey(jobName, jobInfo.getTriggerName());
			
			SimpleTriggerImpl trigger = new SimpleTriggerImpl();
			trigger.setKey(triggerKey);
			trigger.setJobGroup(jobInfo.getGroupName());
			trigger.setJobName(jobName);
			
			trigger.setStartTime(start);
			if (end != null) trigger.setEndTime(end);
			trigger.setRepeatCount(repeatCount == null ? 0 : repeatCount);
			if (repeatInterval != null) trigger.setRepeatInterval(repeatInterval);
			
			try {
				if (scheduler.checkExists(triggerKey) || scheduler.checkExists(jobDetail.getKey())) {
					removeJob(jobInfo, uniqueCode);
				}
				scheduler.scheduleJob(jobDetail, trigger);
				if (scheduler.isShutdown()) scheduler.start();
			} catch (SchedulerException e) {
				System.err.println("MODIFY QUARTZ SIMPLE JOB ERROR: " + e.getMessage());
			}
		}
	}
	
	/**
	 * 创建cron时间任务
	 * @param jobInfo
	 * @param uniqueCode
	 * @param timeExp
	 * @param params
	 */
	public void addJob(JobInfo jobInfo, String uniqueCode, String timeExp, Map<String, Object> params) {
		if (jobInfo == null) return;
		try {
			String jobName = jobName(jobInfo.getName(), uniqueCode);
			
			JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.setName(jobName);
			jobDetail.setGroup(jobInfo.getGroupName());
			jobDetail.setJobClass(jobInfo.getClazz());
			if (params != null && !params.isEmpty()) jobDetail.setJobDataMap(new JobDataMap(params));
			
			CronTriggerImpl cronTrigger = new CronTriggerImpl();
			cronTrigger.setName(jobName);
			cronTrigger.setGroup(jobInfo.getTriggerName());
			cronTrigger.setCronExpression(timeExp);
			
			scheduler.scheduleJob(jobDetail, cronTrigger);
			
			if (scheduler.isShutdown()) scheduler.start();
		} catch (Exception e) {
			System.err.println("ADD QUARTZ JOB ERROR: " + e.getMessage());
		}
	}
	
	/**
	 * 修改任务时间
	 * @param jobInfo
	 * @param uniqueCode
	 * @param time
	 */
	public void modifyJobTime(JobInfo jobInfo, String uniqueCode, String time) {
		try {
			TriggerKey triggerKey = new TriggerKey(jobName(jobInfo.getName(), uniqueCode), jobInfo.getTriggerName());
			CronTriggerImpl cronTrigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
			if (cronTrigger == null) return;
			
			String oldTime = cronTrigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				cronTrigger.setCronExpression(time);
				scheduler.resumeTrigger(triggerKey);
			}
		} catch (Exception e) {
			System.err.println("MODIFY QUARTZ CRON JOB TIME ERROR: " + e.getMessage());
		}
	}
	
	/**
	 * 修改任务
	 * @param jobInfo
	 * @param uniqueCode
	 * @param time
	 * @param params
	 */
	public void modifyJob(JobInfo jobInfo, String uniqueCode, String time, Map<String, Object> params) {
		try {
			String jobName = jobName(jobInfo.getName(), uniqueCode);
			boolean modify = false;
			if (time == null || time == "") {
				modify = true;
			} else {
				CronTriggerImpl cronTrigger = (CronTriggerImpl) scheduler.getTrigger(new TriggerKey(jobName, jobInfo.getTriggerName()));
				if (cronTrigger == null) return;
				String oldTime = cronTrigger.getCronExpression();
				modify = !oldTime.equalsIgnoreCase(time);
			}
			if (modify) {
				removeJob(jobInfo, uniqueCode);
				addJob(jobInfo, uniqueCode, time, params);
			}
		} catch (Exception e) {
			System.err.println("MODIFY QUARTZ CRON JOB ERROR: " + e.getMessage());
		}
	}
	
	/**
	 * 删除任务
	 * @param jobInfo
	 * @param uniqueCode
	 */
	public void removeJob(JobInfo jobInfo, String uniqueCode) {
		try {
			String jobName = jobName(jobInfo.getName(), uniqueCode);
			TriggerKey triggerKey = new TriggerKey(jobName, jobInfo.getTriggerName());
			scheduler.pauseTrigger(triggerKey);
			scheduler.unscheduleJob(triggerKey);
			scheduler.deleteJob(new JobKey(jobName, jobInfo.getGroupName()));
		} catch (Exception e) {
			System.err.println("REMOVE QUARTZ JOB TIME ERROR: " + e.getMessage());
		}
	}
	
	/**
	 * 开启任务
	 */
	public void startJobs() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			
		}
	}
	
	/**
	 * 关闭任务
	 */
	public void shutdownJobs() {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			
		}
	}
	
	/**
	 *  唯一码
	 * @param args
	 * @return
	 */
	public String uniqueCode(Object... args) {
		StringBuilder sb = new StringBuilder();
		sb.append(new Date().getTime());
		for (Object arg: args) {
			sb.append("-").append(arg);
		}
		return sb.toString();
	}
	
	/**
	 * 任务名称
	 * @param baseName
	 * @param uniqueCode
	 * @return
	 */
	private String jobName(String baseName, String uniqueCode) {
		if (uniqueCode == null) return baseName;
		
		return baseName + "-" + uniqueCode;
	}
	
	/**
	 * 校验时间
	 * @param startTime
	 * @return
	 */
	private boolean isValidExpression(final Date startTime) {
		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		trigger.setStartTime(startTime);
		
		Date date = trigger.computeFirstFireTime(null);
		
		return date != null && date.after(new Date());
	}

}
