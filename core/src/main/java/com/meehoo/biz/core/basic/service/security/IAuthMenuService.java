package com.meehoo.biz.core.basic.service.security;


import com.meehoo.biz.core.basic.ro.security.AuthRoleMenuRO;
import com.meehoo.biz.core.basic.ro.security.SetDefinesRO;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.security.AuthMenuTreeVO;

/**
 * Created by CZ on 2017/10/25.
 */
public interface IAuthMenuService extends IBaseService {

    /**
     * 显示授权菜单
     *
     * @return
     * @throws Exception
     */
    AuthMenuTreeVO getMenuListByRoleId(String roleId) throws Exception;

    // 设置自定义权限
    void setDefine(SetDefinesRO ro);
    /**
     * 保存一条角色菜单授权信息
     * @param authRoleMenuRO
     * @throws Exception
     */
    void saveOrUpdateAuthMenu(AuthRoleMenuRO authRoleMenuRO) throws Exception;

    /**
     * 显示当前登录用户当前组织机构下的有权菜单
     *
     * @return
     * @throws Exception
     */
    AuthMenuTreeVO getMenuListByOrgUserId(String orgId, String userId) throws Exception;

    /**
     * 根据用户在某项目担任的角色，显示的菜单
     * @param projectId
     * @param userId
     * @return
     * @throws Exception
     */
    AuthMenuTreeVO getMenuListByProjectId(String projectId, String userId) throws Exception;

    /**
     * 根据管理员Id，显示菜单
     * @param adminId
     * @return
     * @throws Exception
     */
    AuthMenuTreeVO getMenuListByAdminId(String adminId) throws Exception;
}
