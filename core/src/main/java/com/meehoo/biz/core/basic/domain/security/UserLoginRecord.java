package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangjian on 2017/7/6.\
 * 用户登录状态token
 */
@Entity
@Table(name = "sec_login_record")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class UserLoginRecord extends IdEntity {
    public static final Integer LOGOUT= 0;//已退出
    public static final Integer LOGIN = 1;//已登入

    /**
     * 用ID作为token使用
     */

    /**
     * 登录的adminId
     */
    @Column(nullable = false)
    private String userId;

    /**
     * 状态
     * 0 已登出
     * 1 已登录
     */
    @Column(columnDefinition = "INT default 0")
    private Integer status;

    /**
     * 登录时间
     */
    @Column
    private Date loginTime;

    /**
     * 有效时间
     */
    @Column
    private Date availableTime;

    @Column
    private String device;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(Date availableTime) {
        this.availableTime = availableTime;
    }
}
