package com.meehoo.biz.core.basic.domain.setting;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 数据字典的值
 *
 * @author xg
 * @create 2017-05-11-10:36
 */
@Entity
@Table(name = "sys_dict_value")
@DynamicInsert
@DynamicUpdate
@Setter
@Getter
public class DictValue extends IdEntity {

    //是系统预设
    public static final int ISSYSSET_YES = 1;
    //非系统预设
    public static final int ISSYSSET_NO = 0;

//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;

    /**
     * 键
     */
    @Column(length = 64)
    private String mkey;

    /**
     * 值
     */
    @Column(length = 255)
    private String mvalue;

    /**
     * 所属类型  外键
     */
    @ManyToOne
    private DictType dictType;


    /**
     * 是否系统预设
     */
    @Column
    private int isSysSet = ISSYSSET_NO;

}
