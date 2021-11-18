package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户-组织机构-角色关联模型
 * Created by CZ on 2018/1/16.
 */
@Entity
@Table(name = "sec_user_org_role")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
public class UserOrganizationRole extends IdEntity {

    @ManyToOne
    private UserOrganization userOrganization;

    @ManyToOne
    private Role role;
}