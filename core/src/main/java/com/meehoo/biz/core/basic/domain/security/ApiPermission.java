package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 访问API权限
 * @author zc
 * @date 2020-04-20
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "biz_permission_api")
@DynamicInsert
@DynamicUpdate
@Data
public class ApiPermission extends IdEntity {
    @Column(length = 20)
    private String name;

    @Column
    private String description;
}
