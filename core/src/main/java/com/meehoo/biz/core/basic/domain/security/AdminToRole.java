package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author zc
 * @date 2019-04-24
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin_role")
@Data
@DynamicInsert
@DynamicUpdate
public class AdminToRole extends BaseEntity {
    @ManyToOne
    private Admin admin;

    @ManyToOne
    private Role role;
}
