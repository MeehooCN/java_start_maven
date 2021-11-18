package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StreamUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.bos.IMenuDao;
import com.meehoo.biz.core.basic.dao.security.IAuthRoleMenuDao;
import com.meehoo.biz.core.basic.domain.bos.Menu;
import com.meehoo.biz.core.basic.domain.security.*;
import com.meehoo.biz.core.basic.ro.security.AuthRoleMenuRO;
import com.meehoo.biz.core.basic.ro.security.SetDefinesRO;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.bos.MenuVO;
import com.meehoo.biz.core.basic.vo.security.AuthMenuTreeVO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by CZ on 2017/10/25.
 */
@Service
@Transactional
public class AuthMenuServiceImpl extends BaseService implements IAuthMenuService {
    private final IAuthRoleMenuDao authRoleMenuDao;

    private final IUserService userService;

    private final IMenuDao menuDao;

    @Autowired
    public AuthMenuServiceImpl(IAuthRoleMenuDao authRoleMenuDao,
                               IUserService userService,
                               IMenuDao menuDao) {
        this.authRoleMenuDao = authRoleMenuDao;
        this.userService = userService;
        this.menuDao = menuDao;
    }

    @Override
    public AuthMenuTreeVO getMenuListByRoleId(String roleId) throws Exception {

        Role role = queryById(Role.class,roleId);

        AuthMenuTreeVO authMenuTreeVO = new AuthMenuTreeVO();
        //查出用户对应类型的所有的菜单
        List<Menu> menuList = menuDao.queryByMenuType(role.getRoleType());
        List<Menu> topMenus = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getStatus() == Menu.STATUS_ENABLE) {
                if (menu.getGrade() == Menu.GRADE_ROOT) {
                    topMenus.add(menu);
                } else {
                    menu.getParentMenu().addChildMenu(menu);
                }
            }
        }
        topMenus.sort(Comparator.comparingInt(Menu::getSeq));
        authMenuTreeVO.setChildren(VOUtil.convertDomainListToTempList(topMenus, MenuVO.class));

        //查出已授权的菜单
        List<AuthRoleMenu> authRoleMenuList = this.authRoleMenuDao.queryByRoleId(roleId);
        List<String> checkedIdList = new ArrayList<>(authRoleMenuList.size());
        if (BaseUtil.collectionNotNull(authRoleMenuList)) {
            for (AuthRoleMenu authRoleMenu : authRoleMenuList) {
                Menu menu = authRoleMenu.getMenu();
                if (menu != null) {
                    checkedIdList.add(menu.getId());
                }
            }
        }
        authMenuTreeVO.setChecked(checkedIdList);
        return authMenuTreeVO;
    }

    @Override
    public void setDefine(SetDefinesRO ro) {
        // 删除旧有的
        DetachedCriteria dc = DetachedCriteria.forClass(Role2MenuDefine.class);
        dc.add(Restrictions.eq("role.id",ro.getRoleId()));
        List list = list(dc);
        batchDelete(list);
        //
        if (BaseUtil.listNotNull(ro.getMenuDefineIds())){
            Role role = queryById(Role.class, ro.getRoleId());

            List<MenuDefine> menuDefines = queryByIds(MenuDefine.class, ro.getMenuDefineIds());
            List<Role2MenuDefine> collect = menuDefines.stream().map(e -> {
                Role2MenuDefine role2MenuDefine = new Role2MenuDefine();
                role2MenuDefine.setRole(role);
                role2MenuDefine.setMenuDefine(e);
                return role2MenuDefine;
            }).collect(Collectors.toList());
            batchSave(collect);
        }
    }

    @Override
    public void saveOrUpdateAuthMenu(AuthRoleMenuRO authRoleMenuRO) throws Exception {
        authRoleMenuDao.deleteByRoleId(authRoleMenuRO.getRoleId());
        if (BaseUtil.collectionNotNull(authRoleMenuRO.getMenuIdList())) {
            Role role = new Role();
            role.setId(authRoleMenuRO.getRoleId());
            List<Menu> menuList = menuDao.findAllById(authRoleMenuRO.getMenuIdList());
            List<AuthRoleMenu> authRoleMenuList = new ArrayList<>(menuList.size());
            for (Menu menu : menuList) {
                AuthRoleMenu authRoleMenu = new AuthRoleMenu();
                authRoleMenu.setRole(role);
                authRoleMenu.setMenu(menu);
                authRoleMenuList.add(authRoleMenu);
            }
            authRoleMenuDao.saveAll(authRoleMenuList);
        }
    }

    @Override
    public AuthMenuTreeVO getMenuListByOrgUserId(String orgId, String userId) throws Exception {
        AuthMenuTreeVO authMenuTreeVO = new AuthMenuTreeVO();
        //1、查询出当前组织机构当前登录用户下的角色列表
//        List<Role> roleListOfUser = userService.getRolesByUserAndOrg(userId, orgId);
//        List<String> roleIdList = StreamUtil.extractFieldToList(roleListOfUser, Role::getId);
        User user = queryById(User.class, userId);
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(user.getRoleId());

        if (BaseUtil.collectionNotNull(roleIdList)) {
            //2、根据角色列表获取有权菜单
            List<AuthRoleMenu> authRoleMenuList = this.authRoleMenuDao.queryByRoleIdIn(roleIdList);
            //菜单去重，即多个角色相同菜单的情况
            Set<Menu> authMenuSet = StreamUtil.extractFieldToSet(authRoleMenuList, AuthRoleMenu::getMenu);
            Set<Menu> topMenuSetOfUser = new HashSet<>();
            //向上寻找正常启用了的根菜单
            for (Menu authMenu : authMenuSet) {
                Menu searchMenu = authMenu;
                //非根且启用了的菜单才能向上搜索
                while (searchMenu.getGrade() != Menu.GRADE_ROOT && searchMenu.getStatus() == Menu.STATUS_ENABLE) {
                    Menu parentMenu = searchMenu.getParentMenu();
                    parentMenu.addChildMenu(searchMenu);
                    searchMenu = parentMenu;
                }
                if (searchMenu.getGrade() == Menu.GRADE_ROOT && searchMenu.getStatus() == Menu.STATUS_ENABLE) {
                    topMenuSetOfUser.add(searchMenu);
                }

            }
            List<Menu> topMenuListOfUser = new ArrayList<>(topMenuSetOfUser);
            topMenuListOfUser.sort(Comparator.comparingInt(Menu::getSeq));
            //过滤被禁用的菜单
            authMenuTreeVO.setChildren(VOUtil.convertDomainListToTempListWithFilter(topMenuListOfUser, MenuVO.class, menu -> menu.getStatus() == Menu.STATUS_ENABLE));
        } else {
            authMenuTreeVO.setChildren(new ArrayList<>(0));
        }
        return authMenuTreeVO;
    }

    @Override
    public AuthMenuTreeVO getMenuListByProjectId(String projectId, String userId) throws Exception {
        // 根据projectId,userId查到ProjectUser
//        ProjectUser projectUser = projectUserDao.getByProAndUser(projectId, userId);
//        // 拿到roleIdList
//        List<ProUserRole> proUserRoles = proUserRoleDao.getByProjectUser(projectUser.getId());
//        List<String> roleIdList = new ArrayList<>();
//        for (ProUserRole pus:proUserRoles){
//            roleIdList.add(pus.getRole().getId());
//        }
//
//        AuthMenuTreeVO authMenuTreeVO = new AuthMenuTreeVO();
//        if (BaseUtil.collectionNotNull(roleIdList)) {
//            //2、根据角色列表获取有权菜单
//            List<AuthRoleMenu> authRoleMenuList = this.authRoleMenuDao.queryByRoleIdIn(roleIdList);
//            //菜单去重，即多个角色相同菜单的情况
//            Set<Menu> authMenuSet = StreamUtil.extractFieldToSet(authRoleMenuList, AuthRoleMenu::getMenu);
//            Set<Menu> topMenuSetOfUser = new HashSet<>();
//            //向上寻找正常启用了的根菜单
//            for (Menu authMenu : authMenuSet) {
//                Menu searchMenu = authMenu;
//                //非根且启用了的菜单才能向上搜索
//                while (searchMenu.getGrade() != Menu.GRADE_ROOT && searchMenu.getStatus() == Menu.STATUS_ENABLE) {
//                    Menu parentMenu = searchMenu.getParentMenu();
//                    parentMenu.addChildMenu(searchMenu);
//                    searchMenu = parentMenu;
//                }
//                if (searchMenu.getGrade() == Menu.GRADE_ROOT && searchMenu.getStatus() == Menu.STATUS_ENABLE) {
//                    topMenuSetOfUser.add(searchMenu);
//                }
//
//            }
//            List<Menu> topMenuListOfUser = BaseUtil.convertSetToList(topMenuSetOfUser, Menu::getSeq);
//            topMenuListOfUser.sort(Comparator.comparingInt(Menu::getSeq));
//            //过滤被禁用的菜单
//            authMenuTreeVO.setChildren(VOUtil.convertDomainListToTempListWithFilter(topMenuListOfUser, MenuVO.class, menu -> menu.getStatus() == Menu.STATUS_ENABLE));
//        } else {
//            authMenuTreeVO.setChildren(new ArrayList<>(0));
//        }
//        return authMenuTreeVO;
        return null;
    }

    @Override
    public AuthMenuTreeVO getMenuListByAdminId(String adminId) throws Exception{
        Admin admin = queryById(Admin.class,adminId);
        AuthMenuTreeVO authMenuTreeVO = new AuthMenuTreeVO();
        if (StringUtil.stringNotNull(admin.getRoleId())) {
            //2、根据角色列表获取有权菜单
            List<AuthRoleMenu> authRoleMenuList = this.authRoleMenuDao.queryByRoleId(admin.getRoleId());
            //菜单去重，即多个角色相同菜单的情况
            Set<Menu> authMenuSet = StreamUtil.extractFieldToSet(authRoleMenuList, AuthRoleMenu::getMenu);
            Set<Menu> topMenuSetOfUser = new HashSet<>();
            //向上寻找正常启用了的根菜单
            for (Menu authMenu : authMenuSet) {
                Menu searchMenu = authMenu;
                //非根且启用了的菜单才能向上搜索
                while (searchMenu.getGrade() != Menu.GRADE_ROOT && searchMenu.getStatus() == Menu.STATUS_ENABLE) {
                    Menu parentMenu = searchMenu.getParentMenu();
                    parentMenu.addChildMenu(searchMenu);
                    searchMenu = parentMenu;
                }
                if (searchMenu.getGrade() == Menu.GRADE_ROOT && searchMenu.getStatus() == Menu.STATUS_ENABLE) {
                    topMenuSetOfUser.add(searchMenu);
                }

            }
            List<Menu> topMenuListOfUser = new ArrayList<>(topMenuSetOfUser);
            topMenuListOfUser.sort(Comparator.comparingInt(Menu::getSeq));
            //过滤被禁用的菜单
            authMenuTreeVO.setChildren(VOUtil.convertDomainListToTempListWithFilter(topMenuListOfUser, MenuVO.class, menu -> menu.getStatus() == Menu.STATUS_ENABLE));
        } else {
            authMenuTreeVO.setChildren(new ArrayList<>(0));
        }
        return authMenuTreeVO;
    }
}
