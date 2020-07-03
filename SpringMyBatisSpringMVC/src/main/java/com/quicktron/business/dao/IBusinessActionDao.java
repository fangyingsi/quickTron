package com.quicktron.business.dao;

import com.quicktron.business.entities.ReportParamInVO;
import org.apache.ibatis.annotations.Param;

public interface IBusinessActionDao {

//    int queryLpnSlotCnt(@Param("_slotCode")String slotCode,@Param("_lpn")String lpn);

    /*上下架LPN
    * */
//    void updateLpnSlotRelat(@Param("_vo")ReportParamInVO inputVo,@Param("_returnMessage")String returnMessage);

    void updateLpnSlotRelat(@Param("_vo")ReportParamInVO inputVo);

    void refreshTask(@Param("_vo")ReportParamInVO inputVo);

    int queryActivePickTask(@Param("_slotCode") String slotCode);

    void releaseBucket(@Param("_vo")ReportParamInVO inputVo);

    void updateAutoScheduleFlag(@Param("_vo")ReportParamInVO inputVo);

}
