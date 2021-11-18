package com.meehoo.biz.core.basic.ro.security;

import lombok.Data;

import java.util.List;

/**
 * @author zc
 * @date 2020-04-20
 */
@Data
public class UserToPermissionRO {
    private String userId;
    private List<String> permissionIds;
}
