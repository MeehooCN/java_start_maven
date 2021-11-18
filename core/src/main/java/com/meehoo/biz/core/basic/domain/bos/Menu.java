package com.meehoo.biz.core.basic.domain.bos;

import com.meehoo.biz.core.basic.domain.IdEntity;
import com.meehoo.biz.core.basic.domain.security.MenuDefine;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 菜单
 * Created by CZ on 2018/7/16.
 */
@Entity
@Table(name = "bos_menu")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
public class Menu extends IdEntity {

    public static final int STATUS_DISABLE = 0;//禁用

    public static final int STATUS_ENABLE = 1;//启用

    public static final int GRADE_ROOT = 0;//根(一级)菜单

    @Column(length = 50)
    private String code;

    /**
     * 序号
     */
    @Column
    private int seq;

    /**
     * 菜单名
     */
    @Column(length = 80)
    private String name;

    /**
     * 功能路径
     */
    @Column
    private String url;

    /**
     * 图标索引
     */
    @Column(length = 100)
    private String icon;

    /**
     * 菜单启用状态
     */
    @Column
    private int status;

    /**
     * 菜单层级，一级菜单为0,依次递增
     */
    @Column
    private int grade;

    /**
     * 父级菜单
     */
    @ManyToOne
    private Menu parentMenu;

    /**
     * 角色有权的子菜单
     */
    @Transient
    private List<Menu> childMenuList;

    /**
     * 菜单分类
     * 0 管理员菜单
     * 1 用户菜单
     */
    @Column(columnDefinition = "INT default 0")
    private Integer menuType;

    @OneToMany(mappedBy = "menu")
    private List<MenuDefine> menuDefineList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void addChildMenu(Menu child) {
        if (child != null) {
            if (childMenuList == null) {
                childMenuList = new ArrayList<>(0);
            }
            childMenuList.add(child);
        }
    }
}
