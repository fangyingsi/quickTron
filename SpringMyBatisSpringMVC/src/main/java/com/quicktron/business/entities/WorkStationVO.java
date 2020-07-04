package com.quicktron.business.entities;

import lombok.Data;

@Data
public class WorkStationVO extends TableBaseVO {

    private String wsCode;

    private String wsName;

    private String jobMode;

    private String letDownFlag;

    private Integer maxTask;

}