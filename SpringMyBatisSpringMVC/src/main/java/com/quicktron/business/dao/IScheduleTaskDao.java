package com.quicktron.business.dao;

import com.quicktron.business.entities.RcsTaskVO;
import com.quicktron.business.entities.ReportParamInVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface IScheduleTaskDao {

   void autoSchedule();

   //Map<String,RcsTaskVO>
   List<RcsTaskVO> getInitBucketTask(@Param("_slot") String slot);

   //更新货架当前点位
   void updateBucket(@Param("_vo") ReportParamInVO inputVo);

}
