package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.core.basic.domain.security.ApiPermission;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;

/**
 * @author zc
 * @date 2020-04-20
 */
@Getter
public class ApiPermissionVO extends IdEntityVO {
    private String name;
    private String description;

    public ApiPermissionVO(ApiPermission apiPermission) {
        super(apiPermission);
        this.name = apiPermission.getName();
        description = apiPermission.getDescription();
    }
}
