package com.quicktron.business.service;

import com.quicktron.common.utils.PageInfo;
import com.quicktron.business.entities.BucketTaskVO;
import com.quicktron.business.entities.ReportParamInVO;

import java.util.List;

/**
 * Created by fangyingsi on 2020-06-30.
 */
public interface IQueryBkSlotSerivce {

    List<BucketTaskVO> queryLpnData(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);

    List<BucketTaskVO> queryInvInOut(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);

    List<BucketTaskVO> queryBucketTask(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);

    List<BucketTaskVO> queryPickTask(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo);
}
