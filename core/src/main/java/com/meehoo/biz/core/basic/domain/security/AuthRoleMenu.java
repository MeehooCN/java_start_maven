package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.bos.Menu;
import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 角色所授权的功能
 * Created by CZ on 2017/10/24.
 */
@Entity
@Table(name = "sec_auth_role_menu")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class AuthRoleMenu extends IdEntity {

    /**
     * 授权的角色
     */
    @ManyToOne
    private Role role;

    /**
     * 授权的功能菜单(即url值不为空)
     */
    @ManyToOne
    private Menu menu;

}
