package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zc
 * @date 2020-04-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiPermissionRO extends IdRO {
    private String name;
    private String description;
}
