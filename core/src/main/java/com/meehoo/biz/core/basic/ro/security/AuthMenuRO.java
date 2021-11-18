package com.meehoo.biz.core.basic.ro.security;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CZ on 2017/10/25.
 */
@Getter
@Setter
public class AuthMenuRO {

    private String roleId;
    private String objectTypeId;
    private int showAll;
}
