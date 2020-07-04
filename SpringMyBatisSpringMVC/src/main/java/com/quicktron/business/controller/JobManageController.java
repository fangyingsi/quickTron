package com.quicktron.business.controller;

import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.service.IBusinessActionService;
import com.quicktron.business.service.IJobManageService;
import com.quicktron.business.service.impl.QueryBkSlotSerivceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobManageController {
    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    @Autowired
    private IJobManageService jobManageService;

    @ResponseBody
    @RequestMapping(value = "/addJob",method= RequestMethod.POST)
    public Map<String, Object> addJob(){
        try{
        //返回值
//        Map<String, Object> responseMap = new HashMap<String, Object>();
//        try {
//            //获取入参到VO中,要传入lpn/slotcode/操作类型/操作人
//            ReportParamInVO paramInVO = new ReportParamInVO();
//            paramInVO.setLpn(map.get("lpn"));
//            paramInVO.setSlotCode(map.get("slotCode"));
//            paramInVO.setUpdateBy(map.get("createBy"));
//            paramInVO.setOperateType("DOWN");
//            jobManageService.addJob();
             jobManageService.addJob("test1","testgp1","trigger1","tgroup1","TaskSubmitJobImpl",1000);
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
//            responseMap.put("returnStatus","fail");
//            responseMap.put("returnMessage","Internal error");
        }
        return null;
    }
}
