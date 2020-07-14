package com.quicktron.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quicktron.business.dao.IBusinessActionDao;
import com.quicktron.business.dao.IScheduleTaskDao;
import com.quicktron.business.entities.RcsBuckInfoVO;
import com.quicktron.business.entities.RcsSendTaskReturnVO;
import com.quicktron.business.entities.RcsTaskVO;
import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.service.IJobHanderService;
import com.quicktron.common.utils.QuicktronException;
import org.apache.commons.lang3.StringUtils;
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
            LOGGER.error("Integer error:"+e.getMessage());
        }
    }

    public boolean sendRcsTask(RcsTaskVO taskVO){
        //任务下发成功就可以算成功，不用再看货架任务更新是否成功，因为后面RCS会主动更新状态
        boolean result = false;
        String URL = "http://192.168.1.47:10080/api/quicktron/rcs/standardized.robot.job.submit";
        LOGGER.info("开始下发任务编码:"+taskVO.getRobotJobId());
        try {
            //下发任务前校验
            if (StringUtils.isEmpty(taskVO.getEndArea()) && StringUtils.isEmpty(taskVO.getEndPoint())) {
                throw new QuicktronException("任务的目标区域、目标点位不能同时为空.");
            }
            if (!"online".equals(taskVO.getLetDownFlag()) && !"standby".equals(taskVO.getLetDownFlag()) && !"offline".equals(taskVO.getLetDownFlag())) {
                throw new QuicktronException("任务的let down flag状态不合法.");
            }
            //请求头
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            headers.setContentType(MediaType.APPLICATION_JSON);
            //                //请求参数
            //                JSONObject paramJson = new JSONObject();
            //                paramJson.put("userCode", "kc001");
            String paramJson = JSON.toJSONString(taskVO);
            HttpEntity<String> formEntity = new HttpEntity<>(paramJson, headers);
            //发送请求
            String rcsReturnStr = restTemplate.postForObject(URL, formEntity, String.class);
            RcsSendTaskReturnVO rcsReturnVo = JSON.parseObject(rcsReturnStr, RcsSendTaskReturnVO.class);
            //如果返回成功、更新任务状态为1，表示已下发；
            ReportParamInVO buckTaskInput = new ReportParamInVO();
            buckTaskInput.setId(Integer.parseInt(rcsReturnVo.getRobotJobId()));
            if ("success".equals(rcsReturnVo.getMsg())) {
                result =true;
                LOGGER.info("任务下发RCS成功"+buckTaskInput.getId());
                buckTaskInput.setBucketStatus("1"); //1为已下发
                businessActionDao.refreshTask(buckTaskInput);
                //操作完成，过程返回success
                if ("success".equals(buckTaskInput.getReturnMessage())) {
                    //写日志
                    LOGGER.info("任务下发RCS后更新货架任务状态成功"+buckTaskInput.getId());
                } else {
                    //写日志
                    LOGGER.info("任务下发RCS后更新货架任务状态失败"+buckTaskInput.getId());
                }
            } else {
                LOGGER.info("任务下发RCS失败"+buckTaskInput.getId());
                //下发任务失败、更新任务的send_count减1
                //不给定任务状态参数，说明是send_count减1
                businessActionDao.refreshTask(buckTaskInput);
                //操作完成，过程返回success
                if ("success".equals(buckTaskInput.getReturnMessage())) {
                    //写日志
                    LOGGER.info("任务下发RCS失败，更新任务可下发次数减1成功.");
                } else {
                    //写日志
                    LOGGER.info("任务下发RCS失败，更新任务可下发次数减1失败."+buckTaskInput.getReturnMessage());
                }
            }
        }catch (Exception e){
            LOGGER.error("下发任务异常,任务ID为:"+taskVO.getRobotJobId()+","+e.getMessage());
        }
        return result;
    }

    /*调用任务下发接口要做成单独的接口，前台也要调用
      调用RCS接口
      2.5.0前版本
      http://[IP:Port]/api/quicktron/wcs/standardized.robot.job.submit
      2.5.0后版本
      http://[IP:Port]/api/quicktron/rcs/standardized.robot.job.submit
    * */
    public void sendRcsTaskJob(){
      try {
            LOGGER.info("获取bucket task中的INIT任务，调用RCS任务下发接口 begin");
            //查询货架任务
            List<RcsTaskVO> initTaskList = scheduleTaskDao.getInitBucketTask("");
            LOGGER.info("待发任务条数："+initTaskList.size());
            for(RcsTaskVO taskVO:initTaskList) {
                sendRcsTask(taskVO);
            }
        } catch (Exception e) {
            LOGGER.error("Integer error:"+e.getMessage());
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
            String URL = "http://127.0.0.1:7777/test/";

            HttpHeaders header = new HttpHeaders();
            header.add("Accept", MediaType.APPLICATION_JSON.toString());
            header.setContentType(MediaType.APPLICATION_JSON);

            //请求参数，看RCS接收什么样的格式参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("warehouseId", "kc001");
            paramJson.put("zoneCode", "wb123");
//            paramJson.put("bucketCode", "wb123");
            HttpEntity<JSONObject> formEntity = new HttpEntity<>(paramJson, header);
            //不传货架编码，查询整个区域的货架信息,发送请求
            String rcsBucketStr =restTemplate.postForObject(URL, formEntity, String.class);
            List<RcsBuckInfoVO> rcsBuckInfoVOList =JSON.parseArray(rcsBucketStr, RcsBuckInfoVO.class);
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
                    //王经理说不定时更新位置了
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
            LOGGER.error("Internal error" + e.getMessage());
        }
    }

}

