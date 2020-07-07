package com.quicktron.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quicktron.business.dao.IBusinessActionDao;
import com.quicktron.business.dao.IScheduleTaskDao;
import com.quicktron.business.entities.RcsBuckInfoVO;
import com.quicktron.business.entities.RcsSendTaskReturnVO;
import com.quicktron.business.entities.RcsTaskVO;
import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.service.IJobHanderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JobHanderServiceImpl implements IJobHanderService{

    private static final Logger LOGGER = Logger.getLogger(JobHanderServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IScheduleTaskDao scheduleTaskDao;

    @Autowired
    private IBusinessActionDao businessActionDao;

    /*开启自动调度，根据工作站的作业模式自动生成货架任务
    * */
    public void autoSchedule(){
        try {
            scheduleTaskDao.autoSchedule();
        }catch(Exception e){
            LOGGER.info("Integer error:"+e.getMessage());
        }
    }

    /*调用任务下发接口要做成单独的接口，前台也要调用
      调用RCS接口
      2.5.0前版本
      http://[IP:Port]/api/quicktron/wcs/standardized.robot.job.submit
      2.5.0后版本
      http://[IP:Port]/api/quicktron/rcs/standardized.robot.job.submit
    * */
    public void sendRcsTask() {
      try {
            String URL = "http://127.0.0.1:9999/pcquery/login";

            LOGGER.info("获取bucket task中的INIT任务，调用RCS任务下发接口 begin");

            //查询货架任务,每次取一条
            List<RcsTaskVO> initTaskList = scheduleTaskDao.getInitBucketTask();
            LOGGER.info("获取到待下发任务.");
            LOGGER.info(initTaskList.toString());
    //        for(RcsTaskVO taskVO:initTaskList){

    //                //下发任务前校验
    //                if(StringUtils.isEmpty(taskVO.getEndArea())&&StringUtils.isEmpty(taskVO.getEndArea())){
    //                    throw new QuicktronException("任务的目标区域、目标点位不能同时为空.");
    //                }
    //                if(!"online".equals(taskVO.getLetDownFlag())&&!"standby".equals(taskVO.getLetDownFlag())&&!"offline".equals(taskVO.getLetDownFlag())){
    //                    throw new QuicktronException("任务的let down flag状态不合法.");
    //                }

                //请求头
                HttpHeaders headers = new HttpHeaders();
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                headers.setContentType(MediaType.APPLICATION_JSON);

                //请求参数
                JSONObject paramJson = new JSONObject();
                //先用登录接口测试
                paramJson.put("userCode", "kc001");
                paramJson.put("passWord", "wb123");
                HttpEntity<JSONObject> formEntity = new HttpEntity<>(paramJson, headers);
                //发送请求---------------这里要改
                //发送请求---------------这里要改
                //发送请求---------------这里要改
    //            String result = restTemplate.postForObject(URL, formEntity, String.class);
    //            LOGGER.info("获取到待下发任务.");

                RcsSendTaskReturnVO rcsReturnVo = restTemplate.postForObject(URL, formEntity, RcsSendTaskReturnVO.class);
                //如果返回成功、更新任务状态为1，表示已下发；
                ReportParamInVO buckTaskInput = new ReportParamInVO();
                if("success".equals(rcsReturnVo.getMsg())){
                    buckTaskInput.setId(Integer.parseInt(rcsReturnVo.getRobotJobId()));
                    buckTaskInput.setBucketStatus("1"); //1为已下发
                    businessActionDao.refreshTask(buckTaskInput);
                    //操作完成，过程返回success
                    if ("success".equals(buckTaskInput.getReturnMessage())) {
                        //写日志
                        LOGGER.info("success");
                    }else {
                        //写日志
                        LOGGER.info("fail");
                    }
                }else {
                    //下发任务失败、更新任务的send_count减1
                    buckTaskInput.setId(Integer.parseInt(rcsReturnVo.getRobotJobId()));
                    //不给定任务状态参数，说明是send_count减1
                    businessActionDao.refreshTask(buckTaskInput);
                    //操作完成，过程返回success
                    if ("success".equals(buckTaskInput.getReturnMessage())) {
                        //写日志
                        LOGGER.info("success");
                    }else {
                        //写日志
                        LOGGER.info("fail");
                    }
                }
        } catch (Exception e) {
            LOGGER.info("Integer error:"+e.getMessage());
        }
    }

    /*获取RCS上货架的实时信息
      API名称	bucket.query
      API描述	查询货架当前状态时调用当前接口
      API URL	2.5.0后：http://[IP:Port]/api/quicktron/rcs/standardized.bucket.query
      入参：
      warehouseId	Long	T	仓库编号
      zoneCode	    String	T	库区编号
      bucketCode	String	F	货架编码
    */
    public void queryRcsBucketInfo() {
        try {
            String URL = "http://[IP:Port]/api/quicktron/rcs/standardized.bucket.query";

            HttpHeaders header = new HttpHeaders();
            header.add("Accept", MediaType.APPLICATION_JSON.toString());
            header.setContentType(MediaType.APPLICATION_JSON);

            //请求参数
            JSONObject paramJson = new JSONObject();
            /**
             *
             *
             */
            paramJson.put("warehouseId", "kc001");
            paramJson.put("zoneCode", "wb123");
//            paramJson.put("bucketCode", "wb123");
            HttpEntity<JSONObject> formEntity = new HttpEntity<>(paramJson, header);
            //不传货架编码，查询整个区域的货架信息,发送请求
            List<RcsBuckInfoVO> rcsBuckInfoVOList = restTemplate.postForObject(URL, formEntity, List.class);
            for(RcsBuckInfoVO rcsBuckInfoVO:rcsBuckInfoVOList) {
                //如果返回成功、更新任务的当前点位、状态
                if ("true".equals(rcsBuckInfoVO.getSuccess())) {
                    ReportParamInVO buckTaskInput = new ReportParamInVO();
                    ReportParamInVO bucketTaskTwo = new ReportParamInVO();

                    buckTaskInput.setId(Integer.parseInt(rcsBuckInfoVO.getRobotJobId()));
                    buckTaskInput.setBucketCode(rcsBuckInfoVO.getBucketCode());//货架编码
                    buckTaskInput.setTaskStatus(rcsBuckInfoVO.getJobState()); //任务状态
                    buckTaskInput.setPointCode(rcsBuckInfoVO.getHomePoint()); //所在点位
                    buckTaskInput.setUpdateBy("1"); //更新人
                    bucketTaskTwo.setBucketCode(buckTaskInput.getBucketCode());

                    //更新任务状态
                    businessActionDao.refreshTask(buckTaskInput);
                    //更新货架点位
                    scheduleTaskDao.updateBucket(bucketTaskTwo);

                    //操作完成，过程返回success
                    if (!"success".equals(buckTaskInput.getReturnMessage()) || !"success".equals(bucketTaskTwo.getReturnMessage())) {
                        //写成功日志
                        return;
                    } else {
                        //写失败日志
                        return;
                    }
                } else {
                    LOGGER.info("query rcs bucket info fail:" + rcsBuckInfoVO.getMessage());
                }
            }
        }catch (Exception e){
            LOGGER.info("Internal error" + e.getMessage());
        }
    }

}

