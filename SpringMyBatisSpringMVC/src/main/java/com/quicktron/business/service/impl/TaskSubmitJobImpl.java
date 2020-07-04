package com.quicktron.business.service.impl;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

@Service
public class TaskSubmitJobImpl implements Job {

    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    public TaskSubmitJobImpl() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("获取bucket task中的INIT任务，调用RCS任务下发接口 begin");

        //查询货架任务
        LOGGER.info("aaaa");

        //调用RCS接口

        //
    }
}

