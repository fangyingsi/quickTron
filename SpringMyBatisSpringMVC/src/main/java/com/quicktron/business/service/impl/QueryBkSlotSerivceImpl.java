package com.quicktron.business.service.impl;

import com.quicktron.business.dao.IQueryBucketSlotDao;
import com.quicktron.business.entities.BucketTaskVO;
import com.quicktron.business.entities.OperateLogVO;
import com.quicktron.business.entities.ReportParamInVO;
import com.quicktron.business.entities.UserVO;
import com.quicktron.business.service.IQueryBkSlotSerivce;
import com.quicktron.common.utils.PageInfo;
import com.quicktron.common.utils.QuicktronException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryBkSlotSerivceImpl implements IQueryBkSlotSerivce {

    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    @Resource
    private IQueryBucketSlotDao queryBucketSlotDao;

    /*
    login
    **/
    public Map<String, Object> login(UserVO paramInVO){
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus","success");
        responseMap.put("returnMessage","ok");

        try{
            //确认工作站是否有效
            int wsCnt =queryBucketSlotDao.queryStationCnt(paramInVO.getWsCode());
            if(wsCnt==0){
                throw new QuicktronException("the station code is invalid.");
            }

            UserVO loginedUser = queryBucketSlotDao.queryUser(paramInVO);
            if(loginedUser == null){
                throw new QuicktronException("账号或密码无效.");
            }

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("rows", loginedUser);
            dataMap.put("total", 1);
            responseMap.put("data",dataMap);
        }catch(Exception e){
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage",e.getMessage());
        }

        return responseMap;
    }

    /*
    库存查询
    **/
    public List<BucketTaskVO> queryLpnData(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo){
        int pageNo = (pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();

        pageInfo.setTotalRecords(queryBucketSlotDao.queryLpnDataCnt(paramInVO));
        return queryBucketSlotDao.queryLpnData(paramInVO,pageNo,pageInfo.getPageSize());
    }

    /*
    出入库查询
    **/
    public List<OperateLogVO> queryInvInOut(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo){
        int pageNo = (pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();

        pageInfo.setTotalRecords(queryBucketSlotDao.queryInvInOutCnt(paramInVO));
        return queryBucketSlotDao.queryInvInOut(paramInVO,pageNo,pageInfo.getPageSize());
    }

    /*
    查询搬运记录
    **/
    public List<BucketTaskVO> queryBucketTask(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo){
        int pageNo = (pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();

        pageInfo.setTotalRecords(queryBucketSlotDao.queryBucketTaskCnt(paramInVO));
        return queryBucketSlotDao.queryBucketTask(paramInVO,pageNo,pageInfo.getPageSize());
    }

    /*
    查询拣货记录
    **/
    public List<BucketTaskVO> queryPickTask(ReportParamInVO paramInVO, PageInfo<BucketTaskVO> pageInfo){

        int pageNo = (pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();
        pageInfo.setTotalRecords(queryBucketSlotDao.queryPickTaskCnt(paramInVO));
        return queryBucketSlotDao.queryPickTask(paramInVO,pageNo,pageInfo.getPageSize());
    }

    /*
    下发拣货任务,勾选、导入
    **/
    public Map<String, Object> batchPickLpn(List<ReportParamInVO> slotLpnList){
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("returnStatus","success");
        responseMap.put("returnMessage","ok");

        try {
            for (ReportParamInVO paramInVO : slotLpnList) {
                //传参中有没有货位，LPN为空的记录
                if (StringUtils.isEmpty(paramInVO.getSlotCode()) || StringUtils.isEmpty(paramInVO.getLpn())) {
                    throw new QuicktronException("the selected slot or lpn is null");
                }

                //货架有没有正在进行的任务
                //如果是导入，一定要货架列到模板中
                int activeTaskCnt = queryBucketSlotDao.queryActiveTaskByBuck(paramInVO.getBucketCode());
                if(activeTaskCnt>0){
                    throw new QuicktronException("there has been a active task of the bucket:"+paramInVO.getBucketCode());
                }
            }
        }catch (Exception e){
            //导入如何提示
            responseMap.put("returnStatus","fail");
            responseMap.put("returnMessage",e.getMessage());
        }

        //放入pick task表
        queryBucketSlotDao.insertPickTask(slotLpnList);

        return responseMap;
    }
}
