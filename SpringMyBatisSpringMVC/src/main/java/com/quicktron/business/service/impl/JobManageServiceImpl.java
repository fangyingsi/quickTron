package com.quicktron.business.service.impl;

import com.quicktron.business.service.IJobManageService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Service
public class JobManageServiceImpl implements IJobManageService {

    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    /*添加任务
    * */
    public boolean addJob(String jobName, String jobGroup, String triggerName, String triggerGroup, String jobClassName, int time) throws SchedulerException {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();

            //Trigger与Job的关系为1:N
            //定义一个Trigger
            Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroup) //定义trigger的name/group
                    .startNow()
                    .withSchedule(simpleSchedule()  //使用SimpleTrigger
                            .withIntervalInSeconds(time)
                            .repeatForever())
                    .build();

            //定义一个JobDetail
//            Class jobClass = Class.forName(jobClassName);
            JobDetail job = newJob(TaskSubmitJobImpl.class).withIdentity(jobName, jobGroup).build();
//            JobDetail job = newJob(jobClass)
//                    .withIdentity(jobName, jobGroup) //定义job的name/group
//                    .build();

            //加入这个调度
            sched.scheduleJob(job, trigger);

            //启动任务
            if(!sched.isShutdown()){
                sched.start();
                LOGGER.info("任务["+triggerName+"] 启动成功.");
                return true;
            }else{
                LOGGER.info("任务["+triggerName+"] 已启动，无需再次启动.");
                return true;
            }
        } catch (SchedulerException e) {
            LOGGER.info(e.getMessage());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        return false;
    }

//    /**
//     * 获取job列表
//     * @return
//     */
//    public List<JobEntity> queryJobs(){
//        return ud.queryJobs();
//    }
//
//    /**
//     * 暂停job
//     * @param jobName job名称
//     * @param jobGroup  jog组
//     */
//    public void pauseJob(String jobName, String jobGroup) {
//        try {
//            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 恢复job
//     * @param jobName job名称
//     * @param jobGroup  jog组
//     */
//    public void resumeJob(String jobName, String jobGroup) {
//        try {
//            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 删除job
//     * @param jobName  job名称
//     * @param jobGroup  job组
//     * @param triggerName  trigger名称
//     * @param triggerGroup  trigger组
//     */
//    public void removeJob(String jobName, String jobGroup, String triggerName, String triggerGroup) {
//        Scheduler sched = scheduler;
//        try {
//            // 停止触发器
//            sched.pauseTrigger(TriggerKey.triggerKey(triggerName,
//                    jobGroup));
//            // 移除触发器
//            sched.unscheduleJob(TriggerKey.triggerKey(triggerName,
//                    jobGroup));
//            // 删除任务
//            sched.deleteJob(JobKey.jobKey(jobName, jobGroup));
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }


}
