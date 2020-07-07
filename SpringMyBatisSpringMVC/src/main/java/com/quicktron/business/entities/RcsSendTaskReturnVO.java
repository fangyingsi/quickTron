package com.quicktron.business.entities;

import lombok.Data;

@Data
public class RcsSendTaskReturnVO {

    private String msg;

    private String code;

    private String success;

    private Integer errorCode;

    private String robotJobId;

}
