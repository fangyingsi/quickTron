package com.quicktron.business.entities;

import lombok.Data;

@Data
public class ReportParamInVO {

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
    /*lpn
     */
    private String lpn;
    /*创建时间
     */
    private String createTime;

    private Integer updateBy;
    /*操作类型
     */
    private String operateType;

}
