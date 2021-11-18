package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author zc
 * @date 2020-12-17
 */
@Entity
@Table(name = "sec_role2menu_define")
@DynamicInsert
@DynamicUpdate
@Setter
@Getter
public class Role2MenuDefine extends IdEntity {
    @ManyToOne
    private Role role;

    @ManyToOne
    private MenuDefine menuDefine;
}
