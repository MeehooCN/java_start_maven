package com.meehoo.biz.mobile.basic;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.MD5Encrypt;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.security.IUserOrganizationDao;
import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.Role;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.domain.security.UserOrganization;
import com.meehoo.biz.core.basic.handler.UserManager;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.bos.SearchConditionListRO;
import com.meehoo.biz.core.basic.ro.security.*;
import com.meehoo.biz.core.basic.service.security.IAdminService;
import com.meehoo.biz.core.basic.service.security.IOrganizationService;
import com.meehoo.biz.core.basic.service.security.IUserService;
import com.meehoo.biz.core.basic.vo.security.OrganizationWithUserVO;
import com.meehoo.biz.core.basic.vo.security.RoleVO;
import com.meehoo.biz.core.basic.vo.security.UserOrganizationVO;
import com.meehoo.biz.core.basic.vo.security.UserVO;
import com.meehoo.biz.mobile.handler.FileUtil;
import com.meehoo.biz.mobile.param.TokenRO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * Created by CZ on 2017/10/20.
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/security/user")
public class UserController {
    private final IUserService userService;
    private final IUserOrganizationDao userOrganizationDao;
    private final IAdminService adminService;
    private final IOrganizationService organizationService;

    @Autowired
    public UserController(IUserService userService, IUserOrganizationDao userOrganizationDao, IAdminService adminService, IOrganizationService organizationService) {
        super();
        this.userService = userService;
        this.userOrganizationDao = userOrganizationDao;
        this.adminService = adminService;
        this.organizationService = organizationService;
    }

    @ApiOperation("获得当前登录用户")
    @PostMapping("getCurrentUser")
    public HttpResult<UserVO> getCurrentUser(@RequestBody TokenRO ro) throws Exception{
        User user = UserManager.getCurrentUser();

        return HttpResult.success(new UserVO(user));
    }


    @ApiOperation("查询人员,并按机构分类")
    @PostMapping("getListByOrg")
    public HttpResult<Map<String,List<OrganizationWithUserVO>>> getListByOrg() throws Exception {
        return HttpResult.success(userService.classifyByOrganization());
    }

    @RequestMapping(value = "getCurrLoginUser", method = RequestMethod.POST)
    public HttpResult<UserVO> getCurrLoginUser(@RequestBody AuthenticationRO authenticationRO) throws Exception {
//        ControllerMessage controllerMessage = new ControllerMessage();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        UserVO userVO = new UserVO(user);
//        controllerMessage.setData(user == null ? "" : new UserVO(user));
        // 查询所属机构的子机构
//        List<OrganizationVO> subOrgList = organizationService.getSubOrgList(user.getOrgId());
//        Organization organization = organizationService.queryById(user.getOrgId());
//        subOrgList.add(new OrganizationVO(organization));
//        UserVO userVO = new UserVO(user);
//        userVO.setOrganizationVOList(subOrgList);
        return HttpResult.success(userVO);
    }

    /**
     * 新建用户信息
     *
     * @param userRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpResult<String> create(@RequestBody UserRO userRO) throws Exception {
        return HttpResult.success(userService.createSaveUser(userRO));
    }

    /**
     * 新建/修改用户信息
     *
     * @param userRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult<String> update(@RequestBody UserRO userRO) throws Exception {
        String msg = userService.updateSaveUser(userRO);
        return HttpResult.success(msg);
    }

    @RequestMapping(value = "saveOrgAndRole", method = RequestMethod.POST)
    public HttpResult saveOrgAndRole(@RequestBody OrganizationRoleRO organizationRoleRO) throws Exception {
//        if (controllerMessage.isNotSuccessMsg()){
//            return controllerMessage.toMap();
//        }
        User user = userService.queryById(User.class, organizationRoleRO.getUserId());
        Role role = userService.queryById(Role.class, organizationRoleRO.getRoleIdList().get(0));
        user.setRoleId(role.getId());
        user.setRoleName(role.getName());
        userService.update(user);
//        userService.saveOrganizationRoleLink(organizationRoleRO);
        return HttpResult.success();
    }

    /**
     * 查询用户所属的机构列表
     *
     * @param idRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getUserOrgList", method = RequestMethod.POST)
    public HttpResult<UserVO> getUserOrgList(@RequestBody IdRO idRO) throws Exception {
        if (StringUtil.stringIsNull(idRO.getId())) {
            throw new RuntimeException("用户id不能为空");
        }
        UserVO userOrgList = userService.getUserOrgList(idRO.getId());
        return HttpResult.success(userOrgList);
    }

    /**
     * 查询用户所属的机构及其对应的角色
     *
     * @param idRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getUserOrgAndRoleList", method = RequestMethod.POST)
    public HttpResult< List<UserOrganizationVO>> getUserOrgAndRoleList(@RequestBody IdRO idRO) throws Exception {
        if (StringUtil.stringIsNull(idRO.getId())) {
            throw new RuntimeException("用户id不能为空");
        }
        List<UserOrganizationVO> userOrgAndRoleList = userService.getUserOrgAndRoleList(idRO.getId());
        return HttpResult.success(userOrgAndRoleList);
    }

//    @RequestMapping(value = "list", method = RequestMethod.POST)
//    public HttpResult<PageResult<UserVO>> list(@RequestBody PageRO pagePO) throws Exception {
//        PageResult<UserVO> userVOPageResult = userService.listPage(pagePO);
//        return HttpResult.success(userVOPageResult);
//    }

    @PostMapping("listAll")
    public HttpResult<List<UserVO>> listAll(@RequestBody SearchConditionListRO searchConditionListRO) throws Exception {
        List<UserVO> userVOS = userService.listAll(searchConditionListRO);
        return HttpResult.success(userVOS);
    }

    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public HttpResult<UserVO> getById(@RequestBody IdRO idRO) throws Exception {
        if (StringUtil.stringIsNull(idRO.getId())) {
           throw new RuntimeException("请选择要查询的用户");
        }
        UserVO userVO = userService.getById(idRO.getId());
        return HttpResult.success(userVO);
    }

    /**
     * 查询用户在某个机构里拥有的角色
     *
     * @param userOrganizationRO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getUserHasRoleList", method = RequestMethod.POST)
    public HttpResult<List<RoleVO>> getUserHasRoleList(@RequestBody UserOrganizationRO userOrganizationRO) throws Exception {
        List<RoleVO> userHasRoleList = userService.getUserHasRoleList(userOrganizationRO.getUserId(), userOrganizationRO.getOrganizationId());
        return HttpResult.success(userHasRoleList);
    }

    /**
     * 重置密码
     */
    @PostMapping("resetPassword")
    public HttpResult<String> resetPassword(@RequestBody IdRO idPO) throws Exception {
        User user = userService.queryById(User.class, idPO.getId());
        if (BaseUtil.objectNotNull(user)) {
            user.setPassword(MD5Encrypt.EncryptPassword("123456"));
            userService.update(user);
            return HttpResult.success("操作成功，密码已被重置为123456");
        } else {
            throw new RuntimeException("未查询到当前用户");
        }
    }

    /**
     * 修改密码
     *
     * @param modifyPasswordRO
     * @return
     */
    @PostMapping(value = "modifyPassword")
    public HttpResult<String> modifyPassword(ModifyPasswordRO modifyPasswordRO) throws Exception{
        User user = userService.queryById(User.class, modifyPasswordRO.getId());
        if (MD5Encrypt.CheckEncryptPassword(modifyPasswordRO.getOldPw(), user.getPassword())) {
            // 密码正确，修改密码
            return judgeUser(user, modifyPasswordRO.getNewPw(), modifyPasswordRO.getConfirmNewPw());
        }else{
            // 密码错误，提示
            throw new RuntimeException("原密码错误!");
        }
    }

    private HttpResult<String> judgeUser(User user, String password, String confirmPassword)throws Exception{
        if(password.equals(confirmPassword)){
            user.setPassword(password);
            userService.update(user);
            return HttpResult.success("修改成功");
        }else{
            throw new RuntimeException("两次密码必须一致");
        }
    }

    private HttpResult<String> judgeAdmin(Admin admin, String password, String confirmPassword)throws Exception{
        if(password.equals(confirmPassword)){
            admin.setPassword(MD5Encrypt.EncryptPassword(password));
            adminService.update(admin);
            return HttpResult.success("修改成功");
        }else{
            throw new RuntimeException("两次密码必须一致");
        }
    }

//    @ApiOperation("检查用户名是否重复")
//    @PostMapping("checkUserName")
//    public HttpResult<String> checkUserName(@RequestBody NameRO nameRO) throws Exception {
//        String userName = nameRO.getName();
//        if (BaseUtil.stringNotNull(userName)) {
//            User user = userService.getUserByName(userName);
//            if (user == null) {
//                return HttpResult.success("用户名可用");
//            } else {
//                throw new RuntimeException("用户名已被使用");
//            }
//        } else {
//            throw new RuntimeException("用户名不能为空");
//        }
//    }


    public HttpResult delete(@RequestBody IdRO idRO) throws Exception {
        // 先删除与机构的中间表数据
        List<UserOrganization> userOrganizationList = userOrganizationDao.findByUserId(idRO.getId());
        if (userOrganizationList.size()>0)
            userService.batchDelete(userOrganizationList);
        userService.delete(idRO);
        return HttpResult.success();
    }

    @RequestMapping(value = "uploadIcon",method = RequestMethod.POST)
    @ApiOperation("上传头像")
    public HttpResult<String> uploadIcon(MultipartFile fileUpload, String empNo) throws Exception{
        User employee = userService.getUserByNumber(empNo);
        String url = FileUtil.uploadFile(fileUpload);
        employee.setHeadImgUrl(url);
        userService.update(employee);
        return HttpResult.success(url);
    }
}
