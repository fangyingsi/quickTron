package com.quicktron.business.dao;

import com.quicktron.business.entities.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IQueryBucketSlotDao {

    /*查询工作站
    * */
    List<WorkStationVO> queryStation(@Param("_wsCode")String wsCode);

    /*查询工作站的点位列表
     * */
    List<WorkStationDtlVO> queryStationDtl(@Param("_wsCode")String wsCode);

    /*查询状态列表
     * */
    List<LookupValueVO> queryLookup(@Param("_lookupType")String lookupType);

    /*查询货架编码LOV
     * */
    List<BucketVO> queryBucket(@Param("_bucket")String bucketCode);

    /*查询货位编码LOV
     * */
    List<SlotVO> querySlot(@Param("_slot")String slotCode,@Param("_lpn")String lpn);

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

    /*根据LPN或货位查询所属货架上货位情况
    * */
    List<String> queryBucketDataByLpn(@Param("_vo")ReportParamInVO paramInVO, @Param("_curPage") int curPage, @Param("_pageSize") int pageSize);
    int queryBucketDataByLpnCnt(@Param("_vo")ReportParamInVO paramInVO);
}
