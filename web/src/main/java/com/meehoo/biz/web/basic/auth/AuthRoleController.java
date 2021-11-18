package com.meehoo.biz.web.basic.auth;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.domain.security.UserContextInfo;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.security.AuthMenuRO;
import com.meehoo.biz.core.basic.ro.security.AuthRoleMenuRO;
import com.meehoo.biz.core.basic.ro.security.AuthenticationRO;
import com.meehoo.biz.core.basic.service.security.IAuthMenuService;
import com.meehoo.biz.core.basic.service.security.IUserService;
import com.meehoo.biz.core.basic.util.UserContextUtil;
import com.meehoo.biz.core.basic.vo.security.AuthMenuTreeVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CZ on 2017/10/25.
 */
@Api(tags = "授权管理")
@RestController
@RequestMapping("/security/authRole")
public class AuthRoleController {

    private final IAuthMenuService authMenuService;

    private final IUserService userService;

    @Autowired
    public AuthRoleController(IAuthMenuService authMenuService,
                              IUserService userService) {
        this.authMenuService = authMenuService;
        this.userService = userService;
    }

    /***
     * 根据角色ID查询所授权的菜单
     * @param authMenuPO
     * @return
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("getMenuListByRoleId")
    public HttpResult<AuthMenuTreeVO> getMenuListByRoleId(@RequestBody AuthMenuRO authMenuPO) throws Exception {
        AuthMenuTreeVO authMenuTreeVO = authMenuService.getMenuListByRoleId(authMenuPO.getRoleId());
        return HttpResult.success(authMenuTreeVO);
    }

    /**
     * 批量保存角色的菜单授权信息
     *
     * @param authRoleMenuRO
     * @return
     * @throws Exception
     */
    @PostMapping("batchSaveAuthMenuInfo")
    public HttpResult<String> batchSaveAuthInfo(@RequestBody AuthRoleMenuRO authRoleMenuRO) throws Exception {
        authMenuService.saveOrUpdateAuthMenu(authRoleMenuRO);
        return HttpResult.success("授权成功");
    }

    /**
     * 获取当前登录用户拥有的菜单列表
     *
     * @return
     */
    @PostMapping("getUserHasMenuList")
    public HttpResult<AuthMenuTreeVO> getRoleHasMenuList(@RequestBody AuthenticationRO authenticationRO) throws Exception{
        User user = UserContextUtil.getUser();
        if (BaseUtil.objectNotNull(user)) {
            UserContextInfo userContextInfo = user.getUserContextInfo();
            userContextInfo.setCurrOrgId(authenticationRO.getCurrOrgId());
            userContextInfo.setCurrOrgName(authenticationRO.getCurrOrgName());

            AuthMenuTreeVO authMenuTreeVO = authMenuService.getMenuListByOrgUserId(userContextInfo.getCurrOrgId(), user.getId());
            return HttpResult.success(authMenuTreeVO);
        } else {
            throw new RuntimeException("未获取到当前登录用户信息");
        }
    }

    @RequestMapping("getMenuList")
    public HttpResult<AuthMenuTreeVO> getRoleHasMenuList(String projectId, String userId) throws Exception{
        AuthMenuTreeVO authMenuTreeVO = authMenuService.getMenuListByProjectId(projectId, userId);
        return HttpResult.success(authMenuTreeVO);
    }
}
