package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 用户与机构多对多
 *
 * Created by CZ on 2018/1/16.
 */
@Entity
@Table(name = "sec_user_organization")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
public class UserOrganization extends IdEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private Organization organization;

    @OneToMany(mappedBy = "userOrganization",fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<UserOrganizationRole> userOrganizationRoleList;
}