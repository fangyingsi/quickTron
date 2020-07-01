package com.quicktron.business.dao;

import com.quicktron.business.entities.BucketTaskVO;
import com.quicktron.business.entities.ReportParamInVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IQueryBucketSlotDao {

    /*库存查询
     * */
    List<BucketTaskVO> queryLpnData(@Param("_vo")ReportParamInVO paramInVO, @Param("_curPage") int curPage, @Param("_pageSize") int pageSize);
    int queryLpnDataCnt(@Param("_vo")ReportParamInVO paramInVO);

    /*出入库查询
     * */
    List<BucketTaskVO> queryInvInOut(@Param("_vo")ReportParamInVO paramInVO, @Param("_curPage") int curPage, @Param("_pageSize") int pageSize);
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
