package com.meehoo.biz.core.basic.domain.security;

import com.meehoo.biz.core.basic.domain.parent.AddressEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织机构
 * Created by CZ on 2018/1/16.
 */
@Entity
@Table(name = "sec_org")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
public class Organization extends AddressEntity {

    public static final int LEVEL_TOP = 0;

    public static final int ISLeaf_NO = 0;

    public static final int ISLeaf_YES = 1;

    public static final int ISDefault_NO = 0;

    public static final int ISDefault_YES = 1;

    /**
     * 机构层次,0为顶层
     */
    @Column
    private int grade = 0;

    /**
     * 是否是子机构
     */
    @Column
    private int isLeaf = ISLeaf_NO;


    /**
     * 项目中的机构类型
     */
    @Column
    private String proOrgType;


    /**
     * 是否系统预设
     * 0  否
     * 1  是
     */
    @Column
    private int isDefault = ISDefault_NO;

    /**
     * 上级机构
     */
    @ManyToOne
    private Organization parentOrg;

    /**
     * 下级机构
     */
    @Transient
    private List<Organization> subOrgList = new ArrayList<>();

    @Column(length = 20)
    private String contactPerson; // 联系人

    @Column(length = 20)
    private String contactPhone; // 联系电话

    @Column
    private String description;
}