package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.TimeEntityRO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zc
 * @date 2019-04-26
 */
@Setter
@Getter
public class AdminRO extends TimeEntityRO {
    /**
     * 登录名
     */
    private String userName;

    private String password;

    /**
     * 头像
     */
    private String headPic;

    private String roleId;

    private String roleName;

    private String organizationId;

    private String organizationName;

    private String organizationCode;
}
