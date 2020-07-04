package com.quicktron.business.controller;

import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.entities.UserVO;
import com.quicktron.business.service.IBusinessActionService;
import com.quicktron.business.service.impl.QueryBkSlotSerivceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pcservice")
public class PcBusinessController {

    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    @Autowired
    private IBusinessActionService businessActionService;

    /*手工下架
    * */
    @ResponseBody
    @RequestMapping(value = "/downLpn",method= RequestMethod.POST)
    public Map<String, Object> downLpn(@RequestBody Map<String,String> map){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中,要传入lpn/slotcode/操作类型/操作人
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setLpn(map.get("lpn"));
            paramInVO.setSlotCode(map.get("slotCode"));
            paramInVO.setUpdateBy(map.get("createBy"));
            paramInVO.setOperateType(map.get("OperateType"));
            return businessActionService.updateLpnSlotRelate(paramInVO);
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;
    }

    /*手工上架
     * */
    @ResponseBody
    @RequestMapping(value = "/upLpn",method= RequestMethod.POST)
    public Map<String, Object> upLpn(@RequestBody Map<String,String> map){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中,要传入lpn/slotcode/操作人
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setLpn(map.get("lpn"));
            paramInVO.setSlotCode(map.get("slotCode"));
            paramInVO.setUpdateBy(map.get("createBy"));
            paramInVO.setOperateType(map.get("OperateType"));
            return businessActionService.updateLpnSlotRelate(paramInVO);
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;
    }

    /*给RCS调用刷新任务状态
     * */
    @ResponseBody
    @RequestMapping(value = "/refreshTask",method= RequestMethod.POST)
    public Map<String, Object> refreshTask(@RequestBody Map<String,String> map){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中,要传入 任务ID/货架编码/目标点位/任务状态
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setId(Integer.parseInt(map.get("id")));
            paramInVO.setBucketCode(map.get("bucketCode"));
            paramInVO.setTaskStatus(map.get("taskStatus")); //转换为数字
            paramInVO.setEndPoint(map.get("endPoint"));
            return businessActionService.refreshTask(paramInVO);
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;
    }

    /*查询活动状态的拣货任务
     * */
    @ResponseBody
    @RequestMapping(value = "/queryActivePickTask",method= RequestMethod.POST)
    public Map<String, Object> queryActivePickTask(@RequestBody Map<String,String> map){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中,要传入 货位
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setSlotCode(map.get("slotCode"));
            return businessActionService.releaseBucket(paramInVO);
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;
    }

    /*释放货架,生成货架任务记录
     * */
    @ResponseBody
    @RequestMapping(value = "/releaseBucket",method= RequestMethod.POST)
    public Map<String, Object> releaseBucket(@RequestBody Map<String,String> map){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中,要传入 工作站/货位/目标区域 or 目标点位/操作人
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setWsCode(map.get("wsCode"));
            paramInVO.setSlotCode(map.get("slotCode"));
//            paramInVO.setEndArea(map.get("endArea"));
//            paramInVO.setEndPoint(map.get("endPoint"));
            paramInVO.setCreateBy(map.get("createBy"));
            return businessActionService.releaseBucket(paramInVO);
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;
    }


}
