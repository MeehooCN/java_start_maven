package com.meehoo.biz.core.basic.domain.setting;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 数据字典 类型
 *
 * @author xg
 * @create 2017-05-11-10:06
 */
@Entity
@Table(name = "sys_dict_type")
@DynamicInsert
@DynamicUpdate
@Data
public class DictType extends IdEntity {

    //是系统预设
    public static final int ISSYSSET_YES = 1;
    //非系统预设
    public static final int ISSYSSET_NO = 0;

//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;

    /**
     * 使用模块
     */
    @Column(length = 100)
    private String module;

    /**
     * 名称
     */
    @Column(length = 100)
    private String name;

    /**
     * 类型编码
     */
    @Column(length = 100)
    private String code;


    /**
     * 是否系统预设
     */
    @Column
    private int isSysSet = ISSYSSET_NO;
}
