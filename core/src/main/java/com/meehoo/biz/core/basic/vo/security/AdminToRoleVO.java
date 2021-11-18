package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.core.basic.domain.security.AdminToRole;
import com.meehoo.biz.core.basic.vo.BaseEntityVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zc
 * @date 2019-04-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminToRoleVO extends BaseEntityVO {
    private String adminId;

    private String adminName;

    private String roleId;

    private String roleName;

    public AdminToRoleVO(AdminToRole adminToRole) {
        this.adminId = adminToRole.getAdmin().getId();
        this.adminName = adminToRole.getAdmin().getUserName();
        this.roleId = adminToRole.getRole().getId();
        this.roleName = adminToRole.getRole().getName();
    }
}
