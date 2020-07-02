package com.quicktron.business.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    private Integer userId;

    private String userCode;

    private String userName;

    private String passWord;

    private String wsCode;

}
