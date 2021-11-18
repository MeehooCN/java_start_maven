package com.meehoo.biz.web.basic.auth;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.MD5Encrypt;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.handler.UserManager;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.param.SearchConditionBuilder;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.security.AdminRO;
import com.meehoo.biz.core.basic.ro.security.AdminSearchRO;
import com.meehoo.biz.core.basic.ro.security.ModifyPasswordRO;
import com.meehoo.biz.core.basic.service.security.IAdminService;
import com.meehoo.biz.core.basic.service.security.IAuthMenuService;
import com.meehoo.biz.core.basic.service.security.IOrganizationService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.bos.MenuVO;
import com.meehoo.biz.core.basic.vo.security.AdminVO;
import com.meehoo.biz.core.basic.vo.security.AuthMenuTreeVO;
import com.meehoo.biz.core.basic.vo.security.OrganizationVO;
import com.meehoo.biz.web.basic.common.BaseControllerPlus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wangjian on 2017/8/31.
 */
@Api(tags = "管理员管理")
@RestController
@RequestMapping("/security/admin")
@AllArgsConstructor
public class AdminController extends BaseControllerPlus<Admin, AdminVO, AdminRO> {
    private final IAdminService adminService;
    private final IAuthMenuService authMenuService;
    private final IOrganizationService organizationService;

    @ApiOperation("管理员列表条件查询")
    @GetMapping("list")
    public HttpResult<PageResult<AdminVO>> listAdmin(AdminSearchRO ro){
        SearchConditionBuilder builder = new SearchConditionBuilder()
                .addLikeStart("organizationCode",ro.getOrganizationCode())
                .addLikeStart("userName",ro.getUserName()).addLikeAny("name",ro.getName());
        return pageWithDisable(builder.toList(),ro.toPageCriteria());
    }

    @ApiOperation("重置密码")
    @PostMapping("resetPwd")
    @ResponseBody
    public HttpResult<String> resetPwd(@RequestBody IdRO idPO) throws Exception {
        Admin admin = adminService.queryById(Admin.class, idPO.getId());
        admin.setPassword(MD5Encrypt.EncryptPassword("123456"));
        adminService.update(admin);
        return HttpResult.success("操作成功，密码已被重置为123456");
    }

    @ApiOperation("修改密码")
    @PostMapping("modifyPassword")
    @ResponseBody
    public HttpResult<String> modifyPassword(@RequestBody ModifyPasswordRO modifyPasswordVO) throws Exception{
        Admin admin = adminService.queryById(Admin.class, modifyPasswordVO.getId());
        if (MD5Encrypt.CheckEncryptPassword(modifyPasswordVO.getOldPw(), admin.getPassword())) {
            // 密码正确，修改密码
            admin.setPassword(MD5Encrypt.EncryptPassword(modifyPasswordVO.getNewPw()));
            adminService.update(admin);
            return HttpResult.success("修改密码成功！");
        } else {
            // 密码错误，提示
            throw new RuntimeException("原密码错误！");
        }
    }

    @ApiOperation("为管理员设置权限")
    @PostMapping("setRole")
    public HttpResult<Boolean> setRole(String adminId, String roleId, String roleName) throws Exception {
        Admin admin = adminService.queryById(Admin.class,adminId);
        admin.setRoleId(roleId);
        admin.setRoleName(roleName);
        adminService.update(admin);
        return HttpResult.success(true);
    }

    /**
     * 新建管理员
     * @param ro
     * @return
     * @throws Exception
     */
    @PostMapping("create")
    @ResponseBody
    @Override
    public HttpResult<String> create(@RequestBody AdminRO ro) throws Exception{
        if(BaseUtil.objectNotNull(adminService.queryByUserName(ro.getUserName()))){
            throw new RuntimeException("用户名已存在");
        }
        if (StringUtil.stringIsNull(ro.getId()))
            ro.setPassword(MD5Encrypt.EncryptPassword("123456"));
        return super.create(ro);
    }

//    /**
//     * 编辑管理员
//     * @param ro
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("update")
//    @ResponseBody
//    @Override
//    public HttpResult update(@RequestBody AdminRO ro) throws Exception{
//        Admin admin = adminService.queryById(Admin.class,ro.getId());
//        Admin ifExist = adminService.queryByUserName(ro.getUserName());
//        if(BaseUtil.objectNotNull(ifExist) && !admin.getId().equals(ifExist.getId())){
//            throw new RuntimeException("未找到id");
//        }
//        super.update(ro);
////        admin.setUsername(vo.getUsername())
////                .setHeadPic(vo.getHeadPic())
////                .setUpdateTime(new Date());
////        adminService.update(admin);
//        return HttpResult.success();
//    }

    @PostMapping("getCurrLoginAdmin")
    public HttpResult<AdminVO> getCurrLoginAdmin() throws Exception {
//        ControllerMessage controllerMessage = new ControllerMessage();
        Admin user = UserManager.getCurrentAdmin();
//        controllerMessage.setData(user == null ? "" : new UserVO(user));
        // 查询所属机构的子机构
        List<Organization> subOrgList = organizationService.getAllSubOrg(user.getOrganizationCode());
//        Organization organization = organizationService.queryById(user.getOrganizationId());
//        if (organization!=null)
//            subOrgList.add(new OrganizationVO(organization));
        AdminVO userVO = new AdminVO(user);
        userVO.setOrganizationVOS(VOUtil.convertDomainListToTempList(subOrgList, OrganizationVO.class));
        return HttpResult.success(userVO);
    }

//    /**
//     * 删除管理员
//     * @param ro
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("delete")
//    @ResponseBody
//    @Override
//    public HttpResult delete(@RequestBody IdRO ro) throws Exception{
//        adminService.deleteById(Admin.class,ro.getId());
//        return HttpResult.success();
//    }


    /**
     * 获取当前登录的管理员用户的菜单
     * @return
     */
    @PostMapping("getAdminHasMenuList")
    @ResponseBody
    public HttpResult<AuthMenuTreeVO> getRoleHasMenuList() throws Exception{
        Admin admin = UserManager.getCurrentAdmin();
        AuthMenuTreeVO menuListByAdminId = authMenuService.getMenuListByAdminId(admin.getId());

//        if (StringUtil.stringNotNull(context_path)){
//            for (MenuVO menuVO : menuListByAdminId.getChildren()) {
//                fillUrl(menuVO,context_path);
//            }
//        }

        return HttpResult.success(menuListByAdminId);
    }


    private void fillUrl(MenuVO menuVO, String deployPath){
        String url = menuVO.getUrl();
        if (StringUtil.stringNotNull(url)){
            menuVO.setUrl(deployPath+url);
        }
        for (MenuVO vo : menuVO.getChildren()) {
            fillUrl(vo,deployPath);
        }
    }
}
