package com.quicktron.business.dao;

import com.quicktron.business.entities.BucketTaskVO;
import com.quicktron.business.entities.OperateLogVO;
import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.entities.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IQueryBucketSlotDao {

    /*查询工作站
    * */
    int queryStationCnt(@Param("_wsCode")String wsCode);

    /*查询login user
     * */
    UserVO queryUser(@Param("_vo")UserVO user);

    /*库存查询
     * */
    List<BucketTaskVO> queryLpnData(@Param("_vo")ReportParamInVO paramInVO, @Param("_curPage") int curPage, @Param("_pageSize") int pageSize);
    int queryLpnDataCnt(@Param("_vo")ReportParamInVO paramInVO);

    /*出入库查询
     * */
    List<OperateLogVO> queryInvInOut(@Param("_vo")ReportParamInVO paramInVO, @Param("_curPage") int curPage, @Param("_pageSize") int pageSize);
    int queryInvInOutCnt(@Param("_vo")ReportParamInVO paramInVO);

    /*查询搬运任务
     * */
    List<BucketTaskVO> queryBucketTask(@Param("_vo")ReportParamInVO paramInVO, @Param("_curPage") int curPage, @Param("_pageSize") int pageSize);
//    int queryBucketTaskCnt(@Param("_vo")ReportParamInVO paramInVO, @Param("_curPage") int curPage, @Param("_pageSize") int pageSize);
//    List<BucketTaskVO> queryBucketTask(@Param("_vo")ReportParamInVO paramInVO);
    int queryBucketTaskCnt(@Param("_vo")ReportParamInVO paramInVO);


   /*查询拣货任务
   * */
    List<BucketTaskVO> queryPickTask(@Param("_vo")ReportParamInVO paramInVO, @Param("_curPage") int curPage, @Param("_pageSize") int pageSize);
    int queryPickTaskCnt(@Param("_vo")ReportParamInVO paramInVO);

    /*查询货架是否有活动任务
    * * */
    int queryActiveTaskByBuck(@Param("_buck")String buckCode);

    /*查询货架是否有活动任务
     * * */
    int insertPickTask(@Param("_lpnList") List<ReportParamInVO> inputLpnList);
}
