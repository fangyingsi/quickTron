package com.quicktron.business.service;

import com.quicktron.business.entities.OperateLogVO;
import com.quicktron.business.entities.UserVO;
import com.quicktron.common.utils.PageInfo;
import com.quicktron.business.entities.BucketTaskVO;
import com.quicktron.business.entities.ReportParamInVO;

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
}
