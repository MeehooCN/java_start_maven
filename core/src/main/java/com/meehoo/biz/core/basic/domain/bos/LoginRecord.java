package com.meehoo.biz.core.basic.domain.bos;

import com.meehoo.biz.core.basic.domain.security.User;
import lombok.Data;

import java.util.Date;

/**
 * user登录记录
 * @author zc
 * @date 2019-08-01
 */
@Data
public class LoginRecord {
    private Date loginTime; // 登录时间

    private Date expireTime; // 过期时间

    private String userId;

    private String userCode;

    private String userName;

    private String organizationId;

    private String organizationName;

    public LoginRecord(User user) {
        this.userId = user.getId();
        this.userCode = user.getCode();
        this.userName = user.getName();
        this.organizationId = user.getOrgId();
        this.organizationName = user.getOrgName();
    }

    public User toUser(){
        User user = new User();
        user.setId(userId);
        user.setCode(userCode);
        user.setUserName(userName);
        user.setOrgId(organizationId);
        user.setOrgName(organizationName);
        return user;
    }
}
