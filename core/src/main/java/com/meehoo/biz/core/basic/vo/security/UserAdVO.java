package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.core.basic.domain.security.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by CZ on 2018/9/5.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserAdVO {
    private String id;
    private String number; // 认证
    private String name;
    private String phone;
    private String staffId;
    /**
     * 蓝牙地址
     */
    private String blueToothAddress;

    private String icon = "default";

    /**
     * 所属机构名称
     */
    private String orgName;
    private String orgId;

    /**
     * 职务
     */
    private String title;

    /**
     * im签名
     */
    private String imSig;

    /**
     * im签名
     */
    private String userName;

    /**
     * 省市区
     */
    private String location;

    public UserAdVO(User user) throws Exception {
        this.id = user.getId();
        this.staffId = user.getId();
        this.number = user.getCode();
        this.name = user.getName();
        this.phone =user.getPhone();
        this.userName = user.getUserName();
    }
}
