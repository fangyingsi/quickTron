package com.quicktron.business.dao;

import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.entities.WaitForPickVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBusinessActionDao {

//    int queryLpnSlotCnt(@Param("_slotCode")String slotCode,@Param("_lpn")String lpn);

    /*查询工作站下待拣货位
    * */
    List<WaitForPickVO> queryWaitPickByStat(@Param("_wsCode")String  wsCode);

    List<WaitForPickVO> queryWaitPickLpn(@Param("_bucket")String  bucketCode);

    int queryWaitPickLpnCnt(@Param("_bucket")String  bucketCode);

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
