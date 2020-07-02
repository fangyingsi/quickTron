package com.quicktron.business.service;

import com.quicktron.business.entities.ReportParamInVO;

import java.util.List;
import java.util.Map;

public interface IBusinessActionService {

    /*更新LPN\SLOT关系，即上下架
    * */
    Map<String, Object> updateLpnSlotRelate(ReportParamInVO inputVo);

//    /*下发任务接口
//     * */
//    Map<String, Object> sendTaskToRCS(ReportParamInVO inputVo);

    /*更新任务状态
     * */
    Map<String, Object> refreshTask(ReportParamInVO inputVo);

    /*查询活动拣货任务
     * */
    Map<String, Object> queryActivePickTask(String slotCode);

    /*释放货架,生成bucket task 任务
     * */
    Map<String, Object> releaseBucket(ReportParamInVO inputVo);

    /*工作站自动调度开关操作
     * */
    Map<String, Object> autoScheduleAction(ReportParamInVO inputVo);


}
