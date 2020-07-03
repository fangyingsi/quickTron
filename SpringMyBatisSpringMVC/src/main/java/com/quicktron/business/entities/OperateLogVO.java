package com.quicktron.business.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperateLogVO extends TableBaseVO implements Serializable{

    private String logType;

    private String logKey;

    private String attribute1;

    private String createBy;
}
