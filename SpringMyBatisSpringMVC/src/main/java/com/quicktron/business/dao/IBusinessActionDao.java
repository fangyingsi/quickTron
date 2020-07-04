package com.quicktron.business.dao;

import com.quicktron.business.entities.ReportParamInVO;
import org.apache.ibatis.annotations.Param;

public interface IBusinessActionDao {

//    int queryLpnSlotCnt(@Param("_slotCode")String slotCode,@Param("_lpn")String lpn);

    /*上下架LPN
    * */
//    void updateLpnSlotRelat(@Param("_vo")ReportParamInVO inputVo,@Param("_returnMessage")String returnMessage);

    void updateLpnSlotRelat(@Param("_vo")ReportParamInVO inputVo);
    /*刷新任务状态
     * */
    void refreshTask(@Param("_vo")ReportParamInVO inputVo);
    /*查询活动任务
     * */
    int queryActivePickTask(@Param("_slotCode") String slotCode);
    /*释放货架
     * */
    void releaseBucket(@Param("_vo")ReportParamInVO inputVo);
    /*更新自动调度开关
     * */
    void updateAutoScheduleFlag(@Param("_vo")ReportParamInVO inputVo);
    /*根据LPN下发拣货任务
     * */
    void submitPickTask(@Param("_vo")ReportParamInVO inputVo);

}
