package com.meehoo.biz.web.basic.auth;

import com.meehoo.biz.core.basic.domain.bos.Menu;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.service.bos.IMenuService;
import com.meehoo.biz.core.basic.vo.bos.MenuVO;
import com.meehoo.biz.core.basic.vo.setting.MenuRO;
import com.meehoo.biz.web.basic.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jy
 * @Date: 2020/10/13
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/sysManage/menu")
@AllArgsConstructor
public class MenuController extends BaseController<Menu, MenuVO> {
    private final IMenuService menuService;

    @ApiOperation("添加")
    @PostMapping("addMenu")
    public HttpResult addMenu(@RequestBody MenuRO ro) throws Exception{
        Menu menu = menuService.addMenu(ro);
        return HttpResult.success(new MenuVO(menu));
    }

    @ApiOperation("修改")
    @PostMapping("updateMenu")
    public HttpResult updateMenu(@RequestBody MenuRO ro) throws Exception{
        Menu menu = menuService.queryById(Menu.class,ro.getId());
        menu.setName(ro.getName())
                .setIcon(ro.getIcon())
                .setUrl(ro.getUrl());
        if(ro.getStatus() != null){
            menu.setStatus(ro.getStatus());
        }
        menuService.update(menu);
        return HttpResult.success();
    }

    @ApiOperation("查询所有")
    @PostMapping("listAllMenu")
    public HttpResult listAll() throws Exception{
        return HttpResult.success(menuService.listAll());
    }

    @ApiOperation("删除菜单及子菜单")
    @PostMapping("deleteById")
    public HttpResult delete(@RequestBody IdRO ro) throws Exception{
        return HttpResult.success(menuService.deleteIncludeChildren(ro.getId()));
    }
}
