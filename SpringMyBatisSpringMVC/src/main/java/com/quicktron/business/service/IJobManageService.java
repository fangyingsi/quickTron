package com.quicktron.business.service;

import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface IJobManageService {

    boolean addJob(String jobName, String jobGroup, String triggerName, String triggerGroup, String jobClassName, int time) throws SchedulerException;

}
