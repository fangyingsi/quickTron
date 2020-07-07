package com.quicktron.business.entities;

import lombok.Data;

@Data
public class RcsBuckInfoVO {

    /*上游任务号
    * */
    private String robotJobId;

    /*查询状态
    */
    private String success;

    /*信息*/
    private String message;

    /*货架编码
    * */
    private String bucketCode;

    /*货架状态
    * */
    private Float bucketState;

    /*库区编码
    */
    private String zoneCode;

    /*区域编码
    * */
    private String areaCode;

    /*所在点位
    */
    private String homePoint;

    /*货架上的任务状态
    * */
    private String jobState;

    /*货位码
    * */
    private String bucketSlotCode;

    /*仓库id
    * */
    private Long warehouseId;

}
