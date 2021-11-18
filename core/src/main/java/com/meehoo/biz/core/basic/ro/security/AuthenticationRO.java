package com.meehoo.biz.core.basic.ro.security;

import lombok.Getter;
import lombok.Setter;

/**
 * 鉴权参数
 * Created by Administrator on 2017/10/17.
 */
@Getter
@Setter
public class AuthenticationRO {
    private String token;
    private String currOrgId;
    private String currOrgName;
    private String currUserId;
    private String currUserName;
}
