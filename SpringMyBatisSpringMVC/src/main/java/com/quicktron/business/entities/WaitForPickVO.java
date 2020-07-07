package com.quicktron.business.entities;

import lombok.Data;

@Data
public class WaitForPickVO {

    /*工作站点位
    * */
    private String pointCode;

    /*工作站名称
    * */
    private String pointName;

    /*货架编码
     * */
    private String bucketCode;

    /*货架层次
     * */
    private String bucketFloor;

    /*工作站编码
    * */
    private String wsCode;

    /*操作人
    * */
    private String updateBy;

}
