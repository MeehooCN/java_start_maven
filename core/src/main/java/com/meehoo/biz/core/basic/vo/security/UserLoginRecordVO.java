package com.meehoo.biz.core.basic.vo.security;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by qx on 2019/4/1.
 */
@Getter
@Setter
public class UserLoginRecordVO {

    private String id;

    private String userId;

    private Integer status;

    private Date loginTime;

    private Date availableTime;

    private String device;

}
