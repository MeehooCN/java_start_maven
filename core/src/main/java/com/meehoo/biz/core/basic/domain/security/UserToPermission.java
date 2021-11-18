package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户访问api权限
 * @author zc
 * @date 2020-04-20
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "biz_user_permission")
@DynamicUpdate
@DynamicInsert
@Data
public class UserToPermission extends IdEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private ApiPermission apiPermission;
}
