package com.meehoo.biz.core.basic.domain;

import com.meehoo.biz.core.basic.annotation.SerialNumber;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 基础资料公共属性表
 * Created by CZ on 2017/10/31.
 */
@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
public class BaseEntity extends IdEntity {

//    public static final int STATUS_ENABLE = 1;
//
//    public static final int STATUS_FORBID = 0;

    /**
     * 编号
     */
    @SerialNumber
    @Column(length = 50)
    protected String code;

    /**
     * 名称
     */
    @Column(length = 80)
    protected String name;

    /**
     * 是否删除
     */
    @Column(columnDefinition = "int default 0")
    protected int isDelete;

    /**
     * 启用/禁用
     */
    @Column(columnDefinition = "int default 1")
    protected int enable;
}
