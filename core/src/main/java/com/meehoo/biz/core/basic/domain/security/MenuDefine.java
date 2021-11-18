package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.IdEntity;
import com.meehoo.biz.core.basic.domain.bos.Menu;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 菜单自定义元素
 * @author zc
 * @date 2020-12-17
 */
@Entity
@Table(name = "sec_menu_define")
@DynamicUpdate
@DynamicInsert
@Setter
@Getter
public class MenuDefine extends IdEntity {
    @ManyToOne
    private Menu menu;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "int default 0")
    private int isDelete;
}
