package com.meehoo.biz.core.basic.vo.setting;

import com.meehoo.biz.core.basic.ro.IdRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: jy
 * @Date: 2020/10/13
 */
@Getter
@Setter
public class MenuRO extends IdRO {

//    private String code;

//    @ApiModelProperty("序号")
//    private Integer seq;

    @ApiModelProperty("菜单名")
    private String name;

    @ApiModelProperty("功能路径")
    private String url;

    @ApiModelProperty("图标索引")
    private String icon;

    /**
     * 菜单启用状态
     */
    @ApiModelProperty("菜单启用状态 0 禁用;1 启用")
    private Integer status;

//    /**
//     * 菜单层级，一级菜单为0,依次递增
//     */
//    private int grade;

    @ApiModelProperty("父级菜单id")
    private String parentMenuId;

//    /**
//     * 菜单分类
//     * 0 管理员菜单
//     * 1 用户菜单
//     */
//    private Integer menuType;

}
