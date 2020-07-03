package com.quicktron.business.service.impl;

import com.quicktron.business.dao.IBusinessActionDao;
import com.quicktron.business.dao.IQueryBucketSlotDao;
import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.service.IBusinessActionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class BusinessActionServiceImpl implements IBusinessActionService {
    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    @Resource
    private IBusinessActionDao businessActionDao;

    /*上下架lpn
    * */
    public Map<String, Object> updateLpnSlotRelate(ReportParamInVO inputVo) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus", "success");
        responseMap.put("returnMessage", "ok");
        try {
            String returnMessage = "";
            businessActionDao.updateLpnSlotRelat(inputVo);
            //操作完成，过程返回success
            if ("success".equals(inputVo.getReturnMessage())) {
                return responseMap;
            }else {
                //过程返回不成功
                responseMap.put("returnStatus", "fail");
                responseMap.put("returnMessage", inputVo.getReturnMessage());
            }
        } catch (Exception e) {
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus", "fail");
            responseMap.put("returnMessage", "Internal error:"+e.getMessage());
        }
      return responseMap;
    }

    /*给RCS刷新任务状态
    * */
    public Map<String, Object> refreshTask(ReportParamInVO inputVo){
        //初始化返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus", "success");
        responseMap.put("returnMessage", "ok");

        try {
            String returnMessage = "";
            businessActionDao.refreshTask(inputVo);
            //操作完成，过程返回success
            if ("success".equals(inputVo.getReturnMessage())) {
                return responseMap;
            }else {
                //过程返回不成功
                responseMap.put("returnStatus", "fail");
                responseMap.put("returnMessage", inputVo.getReturnMessage());
            }
        } catch (Exception e) {
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus", "fail");
            responseMap.put("returnMessage", "Internal error:"+e.getMessage());
        }
        return responseMap;
    }

    /*根据货位查询是否有活动的拣货任务，弹出提示框
    * */
    public Map<String, Object> queryActivePickTask(String slotCode){

        //初始化返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus", "success");
        responseMap.put("returnMessage", "ok");

        try {
            String returnMessage = "";
            int i = businessActionDao.queryActivePickTask(slotCode);
            //没有活动任务
            if (i == 0 ) {
                return responseMap;
            }else {
                //过程返回不成功
                responseMap.put("returnStatus", "fail");
                responseMap.put("returnMessage", "该货架有未完成的活动任务");
            }
        } catch (Exception e) {
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus", "fail");
            responseMap.put("returnMessage", "Internal error:"+e.getMessage());
        }
        return responseMap;
    }

    /*释放货架,只需指定释放区域，不用点位
     如果returnStatus未warn，弹出提示
     * */
    public Map<String, Object> releaseBucket(ReportParamInVO inputVo){
        //初始化返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus", "success");
        responseMap.put("returnMessage", "ok");

        try {
            String returnMessage = "";
            businessActionDao.releaseBucket(inputVo);
            //操作完成，过程返回success
            if ("success".equals(inputVo.getReturnMessage())) {
                return responseMap;
            }else {
                //过程返回不成功
                responseMap.put("returnStatus", "fail");
                responseMap.put("returnMessage", inputVo.getReturnMessage());
            }
        } catch (Exception e) {
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus", "fail");
            responseMap.put("returnMessage", "Internal error:"+e.getMessage());
        }
        return responseMap;
    }

    /*工作站自动调度开关操作
     * */
    public Map<String, Object> autoScheduleAction(ReportParamInVO inputVo){

        //初始化返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus", "success");
        responseMap.put("returnMessage", "ok");

        try {
            String returnMessage = "";
            businessActionDao.updateAutoScheduleFlag(inputVo);
            //操作完成，过程返回success
            if ("success".equals(inputVo.getReturnMessage())) {
                return responseMap;
            }else {
                //过程返回不成功
                responseMap.put("returnStatus", "fail");
                responseMap.put("returnMessage", inputVo.getReturnMessage());
            }
        } catch (Exception e) {
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus", "fail");
            responseMap.put("returnMessage", "Internal error:"+e.getMessage());
        }
        return responseMap;
    }
}
