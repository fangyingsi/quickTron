package com.quicktron.business.service;

import com.quicktron.business.entities.RcsTaskVO;

public interface IJobHanderService {

    void autoSchedule();

    void sendRcsTaskJob();

    void queryRcsBucketInfo();

    boolean sendRcsTask(RcsTaskVO taskVO);

}
