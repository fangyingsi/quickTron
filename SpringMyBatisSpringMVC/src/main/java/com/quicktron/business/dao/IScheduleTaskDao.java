package com.quicktron.business.dao;

import com.quicktron.business.entities.RcsTaskVO;
import com.quicktron.business.entities.ReportParamInVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IScheduleTaskDao {

   void autoSchedule();

   //Map<String,RcsTaskVO>
   List<RcsTaskVO> getInitBucketTask();

   //更新货架当前点位
   void updateBucket(@Param("_vo") ReportParamInVO inputVo);

}
