package com.meehoo.biz.web.basic.auth;

import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.security.SetDefinesRO;
import com.meehoo.biz.core.basic.service.security.IAuthMenuService;
import com.meehoo.biz.core.basic.service.security.IMenuDefineService;
import com.meehoo.biz.core.basic.vo.bos.AuthDefineVO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zc
 * @date 2020-12-17
 */
@RestController
@RequestMapping("sec/define")
@AllArgsConstructor
public class MenuDefineController {
    private final IAuthMenuService authMenuService;
    private final IMenuDefineService menuDefineService;

//    @ApiOperation("获得角色关联的有页面权限的菜单")
//    @GetMapping("getDefinedMenu")
//    public HttpResult<List<MenuVO>> getDefinedMenu(String roleId){
//        List<MenuVO> definedMenu = authMenuService.getDefinedMenu(roleId);
//        return new HttpResult<>(definedMenu);
//    }

    @ApiOperation("获得有自定义权限的菜单")
    @GetMapping("getMenuWithDefines")
    public HttpResult<AuthDefineVO> getMenuWithDefines(String roleId){
        AuthDefineVO menuWithDefines = menuDefineService.getMenuWithDefines(roleId);
        return HttpResult.success(menuWithDefines);
    }

    @ApiOperation("获得某菜单的所有自定义权限")
    @GetMapping("getDefineByMenu")
    public HttpResult<AuthDefineVO> getDefineByMenu(String menuId, String roleId){
        return HttpResult.success(menuDefineService.getDefineByMenu(menuId, roleId));
    }

    @ApiOperation("为某角色设置自定义权限")
    @PostMapping("setDefines")
    public HttpResult setDefines(@RequestBody SetDefinesRO setDefinesRO){
        authMenuService.setDefine(setDefinesRO);
        return HttpResult.success();
    }


}
