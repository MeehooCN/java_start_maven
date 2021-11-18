package com.meehoo.biz.core.basic.vo.bos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.bos.Menu;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by cz on 2018/07/27.
 *
 * @see Menu
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuVO extends IdEntityVO {

    private String code;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 功能路径
     * url的值不为空时才会在返回的json字符中出现
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String url;

    /**
     * 图标索引
     */
    private String icon;

    /**
     * 菜单启用状态
     */
    private Integer status;

    /**
     * 子菜单
     */
    private List<MenuVO> children;

    /**
     * 菜单类型
     * 0 管理员菜单
     * 1 用户菜单
     */
    private Integer menuType;

    public MenuVO(Menu menu){
        super(menu);
        this.code = menu.getCode();
        this.name = menu.getName();
        this.icon = menu.getIcon();
        this.url = menu.getUrl();
        this.status = menu.getStatus();
        this.menuType = menu.getMenuType();
        List<Menu> childMenuList = menu.getChildMenuList();
        if (BaseUtil.collectionNotNull(childMenuList)) {
            childMenuList.sort(Comparator.comparingInt(Menu::getSeq));
            this.children = VOUtil.convertDomainListToTempList(childMenuList, MenuVO.class);
        } else {
            this.children = Collections.emptyList();
        }
    }

}