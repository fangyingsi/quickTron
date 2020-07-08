package com.quicktron.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quicktron.business.dao.IBusinessActionDao;
import com.quicktron.business.dao.IQueryBucketSlotDao;
import com.quicktron.business.dao.IScheduleTaskDao;
import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.entities.WaitForPickVO;
import com.quicktron.business.service.IBusinessActionService;
import com.quicktron.business.websocket.QuickTWebSocketHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessActionServiceImpl implements IBusinessActionService {
    private static final Logger LOGGER = Logger.getLogger(BusinessActionServiceImpl.class);

    @Resource
    private IBusinessActionDao businessActionDao;

    @Resource
    private IScheduleTaskDao scheduleTaskDao;

    @Autowired
    private QuickTWebSocketHandler quickTWebSocketHandler;

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
            //更新货架任务状态
            businessActionDao.refreshTask(inputVo);

            //操作完成，过程返回success
            if ("success".equals(inputVo.getReturnMessage())) {
                //如果状态是DONE，触发消息发给前端
                //不能先更新状态为99，不然根据货架去找可能找到很多历史任务
                if("DONE".equals(inputVo.getTaskStatus())){
                   //更新货架信息(点位)
                    scheduleTaskDao.updateBucket(inputVo);
                    responseMap = queryWaitPickList(inputVo.getBucketCode());
                    //查到记录
                    if("success".equals(responseMap.get("returnStatus"))){
                        //根据目的点位在哪个工作站，判断谁登录pda绑定了这个工作站并开启自动调度，就弹出消息给谁
                        //给前台发送消息
                        quickTWebSocketHandler.sendMessageToUser(responseMap.get("wsCode").toString(), JSON.toJSONString(responseMap));
                    }
                }
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

    /*根据LPN下发一条拣货任务
     * */
    public Map<String, Object> callByLpn(ReportParamInVO inputVo){

        //初始化返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus", "success");
        responseMap.put("returnMessage", "ok");

        try {
//            String returnMessage = "";
            businessActionDao.submitPickTask(inputVo);
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


    /*货架到站时主动根据货架编码查出拣货任务是哪个工作站的，发消息给工作站登录人员
    * */
    @Override
    public Map<String, Object> queryWaitPickList(String bucketCode) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus","success");
        responseMap.put("returnMessage","ok");

        try{
            List<WaitForPickVO> waitForPickVOList=businessActionDao.queryWaitPickLpn(bucketCode);
            String jsonList =JSON.toJSONString(waitForPickVOList);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("rows", jsonList);
            dataMap.put("total", 1);//不需要分页，写死没关系
            responseMap.put("data",dataMap);
            //取记录中随意一条的 wsCode，即货架到达哪个工作站，找到登录该工作站的会话，发送消息
            for(WaitForPickVO waitForPickVO : waitForPickVOList){
                responseMap.put("wsCode",waitForPickVO.getWsCode());
            }
        }catch(Exception e){
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage",e.getMessage());
        }
        return responseMap;
    }

    /*工作站登录人进去LPN下架页面查询待拣货位
     * */
    public Map<String, Object> queryWaitPickByStat(String wsCode) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus","success");
        responseMap.put("returnMessage","ok");

        try{
            List<WaitForPickVO> waitForPickVOList=businessActionDao.queryWaitPickByStat(wsCode);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("rows", waitForPickVOList);
            dataMap.put("total", 1);//不需要分页，写死没关系
            responseMap.put("data",waitForPickVOList);
        }catch(Exception e){
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage",e.getMessage());
        }
        return responseMap;
    }
}
