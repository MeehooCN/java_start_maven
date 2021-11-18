package com.meehoo.biz.core.basic.ro.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by CZ on 2017/10/25.
 */
@Getter
@Setter
public class AuthRoleMenuRO {

    private String roleId;

    private List<String> menuIdList;
}
