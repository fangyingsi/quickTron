package com.quicktron.business.entities;

import lombok.Data;

@Data
public class BucketTaskVO extends TableBaseVO {
    /**
     *货架编码
     */
    private String bucketCode;
    /**
     *目标区域
     */
    private String endArea;
    /**
     *开始点位
     */
    private String startPoint;
//    /**
//     *lpn编码
//     */
//    private String lpn;
    /**
     *目标点位
     */
    private String endPoint;
    /**
     *是否在线
     */
    private String letDownFlag;
    /**
     *任务状态
     */
    private String taskStatus;
}
