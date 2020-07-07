package com.quicktron.business.service;

import com.quicktron.business.entities.*;
import com.quicktron.common.utils.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by fangyingsi on 2020-06-30.
 */
public interface IQueryBkSlotSerivce {

    Map<String, Object> login(UserVO paramInVO);

    List<BucketTaskVO> queryLpnData(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);

    List<OperateLogVO> queryInvInOut(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);

    List<BucketTaskVO> queryBucketTask(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);

    List<BucketTaskVO> queryPickTask(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);

    Map<String, Object> batchPickLpn(List<ReportParamInVO> slotLpnList);

    List<String> queryBucketData(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);

    Map<String, Object> queryStation(ReportParamInVO paramInVO);

    Map<String, Object> queryStationDtl(ReportParamInVO paramInVO);

    Map<String, Object> queryStatus(LookupValueVO paramInVO);

    Map<String, Object> queryBucketList(ReportParamInVO paramInVO);

    Map<String, Object> querySlotList(ReportParamInVO paramInVO);

}
