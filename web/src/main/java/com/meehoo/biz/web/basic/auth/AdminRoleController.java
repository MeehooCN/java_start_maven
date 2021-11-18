package com.meehoo.biz.web.basic.auth;

import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.AdminToRole;
import com.meehoo.biz.core.basic.domain.security.Role;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.security.AdminToRoleRO;
import com.meehoo.biz.core.basic.service.security.IAdminService;
import com.meehoo.biz.core.basic.vo.security.AdminToRoleVO;
import com.meehoo.biz.web.basic.common.BaseControllerPlus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员具有的权限
 * @author zc
 * @date 2019-04-25
 */
@Api(description = "管理员的权限")
@RestController
@RequestMapping("adminToRole")
@AllArgsConstructor
public class AdminRoleController extends BaseControllerPlus<AdminToRole, AdminToRoleVO, AdminToRoleRO> {
    private final IAdminService adminService;

    @ApiOperation("管理员设置权限")
    @PostMapping("adminRole")
    public HttpResult<String> adminRole(@RequestBody AdminToRoleRO adminToRoleRO) throws Exception {
        Admin admin = new Admin();
        admin.setId(adminToRoleRO.getAdminId());
        List<AdminToRole> adminToRoleList = new ArrayList<>();
        adminToRoleRO.getRoleIdList().stream().forEach(e->{
            AdminToRole adminToRole = new AdminToRole();
            adminToRole.setAdmin(admin);
            Role role = new Role();
            role.setId(e);
            adminToRole.setRole(role);
            adminToRoleList.add(adminToRole);
        });

        adminService.batchSave(adminToRoleList);
        return HttpResult.success("上传成功");
    }
}
