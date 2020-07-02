package com.quicktron.business.controller;

import com.quicktron.business.entities.BucketTaskVO;
import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.entities.UserVO;
import com.quicktron.business.service.IQueryBkSlotSerivce;
import com.quicktron.business.service.impl.QueryBkSlotSerivceImpl;
import com.quicktron.common.utils.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PcQyBuckSlotController {
    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    @Resource
    private IQueryBkSlotSerivce queryBkSlotSerivce;

    /*
    登录
    **/
    @ResponseBody
    @RequestMapping(value = "/login",method=RequestMethod.POST)
    public Map<String, Object> login(@RequestBody Map<String,String> map){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中,要传入usercode、password,wscode
            UserVO paramInVO = new UserVO();
            paramInVO.setUserCode(map.get("userCode"));
            paramInVO.setPassWord(map.get("passWord"));
            paramInVO.setWsCode(map.get("wsCode"));

            return queryBkSlotSerivce.login(paramInVO);

        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;

    }

    /*
    库存查询
    **/
    @ResponseBody
    @RequestMapping(value = "/queryLpnDataList/{pageSize}/{curPage}",method=RequestMethod.POST)
    public Map<String, Object> queryLpnDataList(@RequestBody Map<String,String> map,
                                                @PathVariable("pageSize") int pageSize,
                                                @PathVariable("curPage") int curPage){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setBucketCode(map.get("bucketCode"));
            paramInVO.setSlotCode(map.get("slotCode"));
            paramInVO.setTaskStatus(map.get("taskStatus"));
            paramInVO.setLpn(map.get("lpn"));
            //分页
            PageInfo<BucketTaskVO> pageInfo = new PageInfo<BucketTaskVO>();
            pageInfo.setCurrentPage(curPage);
            pageInfo.setPageSize(pageSize);

            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<BucketTaskVO> buckList = queryBkSlotSerivce.queryLpnData(paramInVO, pageInfo);
            dataMap.put("rows", buckList);
            dataMap.put("total", pageInfo.getTotalRecords());
            //拼接返回值
            responseMap.put("data",dataMap);
            responseMap.put("returnStatus","success");
            responseMap.put("returnMessage","ok");
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;

    }

    /*
    出入库查询
    **/
    @ResponseBody
    @RequestMapping(value = "/queryInvInOutList/{pageSize}/{curPage}",method=RequestMethod.POST)
    public Map<String, Object> queryInvInOutList(@RequestBody Map<String,String> map,
                                                @PathVariable("pageSize") int pageSize,
                                                @PathVariable("curPage") int curPage){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setSlotCode(map.get("slotCode"));
            paramInVO.setOperateType(map.get("operateType"));
            paramInVO.setLpn(map.get("lpn"));
            paramInVO.setCreateTime(new Date(map.get("createTime")));
            //分页
            PageInfo<BucketTaskVO> pageInfo = new PageInfo<BucketTaskVO>();
            pageInfo.setCurrentPage(curPage);
            pageInfo.setPageSize(pageSize);

            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<BucketTaskVO> buckList = queryBkSlotSerivce.queryInvInOut(paramInVO, pageInfo);
            dataMap.put("rows", buckList);
            dataMap.put("total", pageInfo.getTotalRecords());
            //拼接返回值
            responseMap.put("data",dataMap);
            responseMap.put("returnStatus","success");
            responseMap.put("returnMessage","ok");
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;

    }

    /*
    查询搬运记录
    **/
    @ResponseBody
    @RequestMapping(value = "/queryBucketTaskList/{pageSize}/{curPage}",method=RequestMethod.POST)
    public Map<String, Object> queryBucketTaskList(@RequestBody Map<String,String> map,
                                                   @PathVariable("pageSize") int pageSize,
                                                   @PathVariable("curPage") int curPage
                                                   ){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            //获取入参到VO中
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setBucketCode(map.get("bucketCode"));
            paramInVO.setTaskStatus(map.get("taskStatus"));
            paramInVO.setPointCode(map.get("pointCode"));
            paramInVO.setCreateTime(new Date(map.get("createTime")));
            //分页
            PageInfo<BucketTaskVO> pageInfo = new PageInfo<BucketTaskVO>();
            pageInfo.setCurrentPage(curPage);
            pageInfo.setPageSize(pageSize);

            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<BucketTaskVO> buckList = queryBkSlotSerivce.queryBucketTask(paramInVO, pageInfo);
            dataMap.put("rows", buckList);
            dataMap.put("total", pageInfo.getTotalRecords());
            //拼接返回值
            responseMap.put("data",dataMap);
            responseMap.put("returnStatus","success");
            responseMap.put("returnMessage","ok");
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
        return responseMap;

    }

    /*
    查询拣货任务
    **/
    @ResponseBody
    @RequestMapping(value = "/queryPickTaskList/{pageSize}/{curPage}",method=RequestMethod.POST)
    public Map<String, Object> queryPickTaskList(@RequestBody Map<String,String> map,
                                                 @PathVariable("pageSize") int pageSize,
                                                 @PathVariable("curPage") int curPage){
        //返回值
        Map<String, Object> responseMap = new HashMap<String, Object>();
        try {
            ReportParamInVO paramInVO = new ReportParamInVO();
            paramInVO.setBucketCode(map.get("bucketCode"));
            paramInVO.setTaskStatus(map.get("taskStatus"));
            paramInVO.setPointCode(map.get("pointCode"));
            paramInVO.setCreateTime(new Date(map.get("createTime")));

            PageInfo<BucketTaskVO> pageInfo = new PageInfo<BucketTaskVO>();
            pageInfo.setCurrentPage(curPage);
            pageInfo.setPageSize(pageSize);

            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<BucketTaskVO> pickList = queryBkSlotSerivce.queryPickTask(paramInVO, pageInfo);
            dataMap.put("rows", pickList);
            dataMap.put("total", pageInfo.getTotalRecords());
            //拼接返回值
            responseMap.put("data",dataMap);
            responseMap.put("returnStatus","success");
            responseMap.put("returnMessage","ok");
        }catch(Exception e){
            LOGGER.error("Internal error:"+e.getMessage());
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage","Internal error");
        }
      return responseMap;
    }

   /*
    勾选记录下发拣货任务
    mysql不支持传入list参数，所以用java来做逻
    **/
   @ResponseBody
   @RequestMapping(value = "/batchPickLpn",method=RequestMethod.POST)
   public Map<String, Object> batchPickLpn(@RequestBody List<ReportParamInVO> slotLpnList){

       try{
           return queryBkSlotSerivce.batchPickLpn(slotLpnList);
       }catch (Exception e){
           Map<String, Object> responseMap = new HashMap<String, Object>();
           responseMap.put("returnStatus","fail");
           responseMap.put("returnMessage",e.getMessage());
           return responseMap;
       }

    }
}
