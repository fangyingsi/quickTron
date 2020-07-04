package com.quicktron.business.controller;

import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.service.IBusinessActionService;
import com.quicktron.business.service.impl.QueryBkSlotSerivceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pdaservice")
public class PdaBusinessController {

    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    @Autowired
    private IBusinessActionService businessActionService;

    /*pda操作工作站的自动调度开关
    * */
    @ResponseBody
    @RequestMapping(value = "/autoScheduleAction",method= RequestMethod.POST)
    public Map<String, Object> autoScheduleAction(@RequestBody Map<String,String> map){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中,工作站/操作人/开关标志(开启START/关闭CLOSE)
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setWsCode(map.get("wsCode"));
            paramInVO.setUpdateBy(map.get("updateBy"));
            paramInVO.setOperateType(map.get("operateType"));
            return businessActionService.autoScheduleAction(paramInVO);
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;
    }

    /*pda LPN呼叫，只生成一条拣货任务
     * */
    @ResponseBody
    @RequestMapping(value = "/callByLpn",method= RequestMethod.POST)
    public Map<String, Object> callByLpn(@RequestBody Map<String,String> map){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中,工作站/操作人/LPN
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setWsCode(map.get("wsCode"));
            paramInVO.setCreateBy(map.get("createBy"));
            paramInVO.setLpn(map.get("lpn"));
            return businessActionService.callByLpn(paramInVO);
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;
    }

}
