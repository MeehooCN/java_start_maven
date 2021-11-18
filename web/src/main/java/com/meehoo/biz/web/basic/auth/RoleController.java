package com.meehoo.biz.web.basic.auth;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.security.Role;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.param.SearchConditionBuilder;
import com.meehoo.biz.core.basic.ro.security.RoleRO;
import com.meehoo.biz.core.basic.service.security.IRoleService;
import com.meehoo.biz.core.basic.vo.security.RoleVO;
import com.meehoo.biz.web.basic.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 角色管理
 * Created by CZ on 2017/10/20.
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/security/role")
@AllArgsConstructor
public class RoleController extends BaseController<Role, RoleVO> {
    private final IRoleService roleService;

    /**
     * 新建角色信息
     *
     * @param roleRO
     * @return
     * @throws Exception
     */
    @PostMapping("create")
    public HttpResult<String> create(@RequestBody RoleRO roleRO) throws Exception {
        // 判断编号是否重复
//        Role has = roleService.queryByNumber(roleRO.getNumber());
//        if (has!=null){
//            throw new RuntimeException("角色编号重复");
//        }
        Role role = new Role();
//        boolean hasNoNumber = !StringUtil.stringNotNull(roleRO.getNumber());
//        if(hasNoNumber) {
//            role.setCode(commonService.getBizObjectSerialNumber("Role"));
//        }else {
//            role.setCode((roleRO.getNumber()));
//        }
//        role.setCode(roleService.getNextCode());
        role.setEnable(1);
        role.setName(roleRO.getName());
        role.setRemark(roleRO.getRemark());
        role.setRoleType(roleRO.getRoleType());
        role.setSystemDefault(Role.SYSTEMDEFAUFT_NO);
        role.setCreateTime(new Date());
        roleService.update(role);
        return HttpResult.success("新建成功");
    }

    /**
     * 新建/修改角色信息
     *
     * @param roleRO
     * @return
     * @throws Exception
     */
    @PostMapping("update")
    public HttpResult<String> update(@RequestBody RoleRO roleRO) throws Exception {
        Role role;
        if (StringUtil.stringNotNull(roleRO.getId())) {
            role = roleService.queryById(Role.class, roleRO.getId());
            role.setUpdateTime(new Date());
//            role.setCode((roleRO.getNumber()));
            role.setName(roleRO.getName());
            role.setRemark(roleRO.getRemark());
            role.setRoleType(roleRO.getRoleType());
            roleService.update(role);
            return HttpResult.success("修改成功");
        }else {
            throw new RuntimeException("未查询到要更新的角色");
        }
    }

    /**
     * 根据roleType查询
     * @param roleType 0.管理员/1.用户
     * @return
     * @throws Exception
     */
    @PostMapping("listByType")
    public HttpResult<List<RoleVO>> listByType(Integer roleType) throws Exception {
        List<RoleVO> roleVOList = roleService.listByRoleType(roleType);
        return HttpResult.success(roleVOList);
    }

    @ApiOperation("列表查询")
    @GetMapping("list")
    public HttpResult<PageResult<RoleVO>> list(String name, PageCriteria pageCriteria) throws Exception{
        SearchConditionBuilder builder = new SearchConditionBuilder()
                .addLikeStart("name",name).addOrderDesc("createTime");
        return page(builder.toList(),pageCriteria);
    }

    @ApiOperation("列表查询")
    @GetMapping("listAll")
    public HttpResult<List<RoleVO>> listAll() throws Exception{
        SearchConditionBuilder builder = new SearchConditionBuilder().addOrderDesc("createTime");
        return super.list(builder.toList());
    }
}
