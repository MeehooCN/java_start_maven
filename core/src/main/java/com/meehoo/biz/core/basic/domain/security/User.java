package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

/**
 * 一般用户(单角色，单机构)
 * Created by CZ on 2017/10/19.
 */
@Entity
@Table(name = "sec_user_inv")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity {
    public static final int STATUS_FORBID = 0;
    public static final int STATUS_ENABLE = 1;

    public static int IS_SYSTEM_DEFAULT = 1;  //系统预设
    public static int NOT_SYSTEM_DEFAULT = 0;  //不是系统预设

    public static int IS_IMPORT = 1;  //已导入
    public static int NO_IMPORT = 0;  //未导入

    public static int Is_Exist = 0;//存在
    public static int Not_Exist = 1;//不存在
    public static int Is_Quit = 2;//离职

    /**
     * 编号
     */
    @Column(nullable = false, length = 80)
    private String code;

    /**
     * 登陆名
     */
    @Column(nullable = false, length = 80, unique = true)
    private String userName;

    /**
     * 密码
     */
    @Column(nullable = false, length = 80)
    private String password;

    /**
     * 用户姓名
     */
    @Column(length = 80)
    private String name;

    /**
     * 职务
     */
    @Column(length = 100)
    private String title;

    /**
     * 状态值(STATUS_FORBID禁用；STATUS_ENABLE启用)
     */
    @Column
    private int status = STATUS_ENABLE;

    /**
     * 电话
     */
    @Column(length = 80)
    private String phone;

    /**
     * 邮箱
     */
    @Column(length = 80)
    private String email;

    /**
     * 头像Url
     */
    @Column(length = 80)
    private String headImgUrl;

    @Column(length = 50)
    private String orgId; // 所属机构

    @Column(length = 50)
    private String orgName;

    @Column(length = 50)
    private String orgCode;

    @Column(length = 50)
    private String roleId;

    @Column(length = 50)
    private String roleName;

    @Column(columnDefinition = "int default 0")
    private int isDismissed; //是否离职  0 否   1 是

    /**
     * 用户在线时的权限信息
     */
    @Transient
    private UserContextInfo userContextInfo;

    public UserContextInfo getUserContextInfo() {
        if(userContextInfo==null){
            userContextInfo = new UserContextInfo();
        }
        return userContextInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
