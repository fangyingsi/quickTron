package com.quicktron.business.entities;

import lombok.Data;

@Data
public class ReportParamInVO extends TableBaseVO{

    /*工作站编码
     */
    private String wsCode;

    /*jobID拼接
     */
    private String ids;
    /*货架编码
    */
    private String bucketCode;
    /*货架编码
     */
    private String slotCode;
    /*货架状态
     */
    private String bucketStatus;
    /*任务状态
     */
    private String taskStatus;
    /*当前点位
     */
    private String pointCode;
    /*目标点位
     */
    private String endPoint;
    /*目标区域
     */
    private String endArea;
    /*lpn
     */
    private String lpn;
//    /*创建时间
//     */
//    private String createTime;
//
//    private Integer updateBy;
    /*操作类型
     */
    private String operateType;
    /*可下发次数
    */
    private Integer sendCount;

    private String returnMessage;

}
