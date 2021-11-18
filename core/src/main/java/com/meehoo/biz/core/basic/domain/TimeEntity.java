package com.meehoo.biz.core.basic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 带时间字段的entity
 * @author zc
 * @date 2021-06-04
 */
@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
public class TimeEntity extends BaseEntity{
    @Column
    private Date updateTime;

    @Column
    private Date createTime;
}
