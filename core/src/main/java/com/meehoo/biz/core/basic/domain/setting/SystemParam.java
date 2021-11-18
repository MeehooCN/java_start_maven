package com.meehoo.biz.core.basic.domain.setting;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016-12-13.
 */
@Entity
@Table(name="sys_system_param")
@DynamicInsert
@DynamicUpdate
@Data
public class SystemParam extends IdEntity {
    /**
     * 参数编码
     */
    @Column
    private String code;

    /**
     * 参数名称
     */
    @Column
    private String name;

    /**
     * 参数值
     */
    @Column
    private String paramValue;

    /**
     * 参数说明
     */
    @Column
    private String paramDesc;
}
