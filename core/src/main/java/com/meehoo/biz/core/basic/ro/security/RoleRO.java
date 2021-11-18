package com.meehoo.biz.core.basic.ro.security;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CZ on 2017/10/20.
 */
@Getter
@Setter
public class RoleRO {
    private String id;
    private String number;
    private String name;
    private String remark;
    //角色类型  0 管理员 1 用户
    private Integer roleType;
}
