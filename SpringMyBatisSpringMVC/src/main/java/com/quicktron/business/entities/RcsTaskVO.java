package com.quicktron.business.entities;

import lombok.Data;

@Data
public class RcsTaskVO {

    /*任务号
    */
    private String robotJobId;

    /*仓库编号
     */
    private Long warehouseId = Long.parseLong("1");

    /*货架当前所属库区
     */
    private String zoneCode ="kckq";
    /*货架编号
     */
    private String bucketCode;
    /*起始点
     */
    private String startPoint;
    /*作业面朝向
     */
    private Integer workFace = 1;
    /*目标货架的可以停靠的货架区域
     */
    private String endArea;
    /*货架到达的目标坐标点位
     */
    private String endPoint;
    /*任务搬运或操作的对象
     */
    private String transportEntityType ="BUCKET";
    /*是，校验搬运对象编码；否，不校验搬运对象编码，默认值：是，0-不校验，1-校验（默认）
     */
    private Integer checkCode = 1;
    /*在线任务到达地点不放下货架，在线任务放下货架，离线任务放下货架
    {"online"/
     "standby"/
     "offline"}
     */
    private String letDownFlag;

}
