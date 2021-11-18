package com.meehoo.biz.core.basic.domain.setting;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author lixiaobin
 * 平台数据字典
 */
@Entity
@Table(name="sys_data_dict")
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@Data
public class DataDict {

    /**
     * 数据字典，服务专题
     */
    public static final Integer DD_PRIVATE_SERVICE_TOPIC = 1;

    /**
     * 数据字典，婚姻状况
     */
    public static final Integer DD_PATIENT_HEALTH_MARRIAGE = 2;

    /**
     * 数据字典，生育状况
     */
    public static final Integer DD_PATIENT_HEALTH_FERTILITY = 3;

    /**
     * 数据字典，手术和外伤
     */
    public static final Integer DD_PATIENT_HEALTH_OPERATION = 4;

    /**
     * 数据字典，家族病史
     */
    public static final Integer DD_PATIENT_HEALTH_FAMILYHISTORY = 5;

    /**
     * 数据字典，药物过敏
     */
    public static final Integer DD_PATIENT_HEALTH_DRUGALLERGY = 6;

    /**
     * 数据字典，食物和接触物过敏
     */
    public static final Integer DD_PATIENT_HEALTH_FOODALLERGY = 7;

    /**
     * 数据字典，医院类型
     */
    public static final Integer DD_HOSPITAL_TYPE = 8;

    /**
     * 数据字典，医院等级
     */
    public static final Integer DD_HOSPITAL_LEVEL = 9;

    /**
     * 数据字典，医生职称
     */
    public static final Integer DD_DOCTOR_TITLE = 10;

    /**
     * 数据字典，医保类型
     */
    public static final Integer DD_MEDICINE_YIBAO = 11;

    /**
     * 数据字典，药品基本单位
     */
    public static final Integer DD_MEDICINE_UNIT = 12;

    /**
     * 数据字典，药品最小单位
     */
    public static final Integer DD_MEDICINE_MINUNIT = 13;

    /**
     * 数据字典，药品剂型
     */
    public static final Integer DD_MEDICINE_FORMULATION = 14;

    /**
     * 数据字典，服药方式
     */
    public static final Integer DD_MEDICINE_USEWAY = 15;

    /**
     * 数据字典，剂量单位
     */
    public static final Integer DD_MEDICINE_DOSAGEUNIT = 16;

    /**
     * 数据字典，合同模板
     */
    public static final Integer DD_CONTRACT_TEMPLATE = 17;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 字典类型
     * G里面的预先定义的类型
     */
    @Column
    private Integer dType;

    /**
     * code
     */
    @Column(nullable = false, length = 50)
    private String dCode;

    /**
     * value
     */
    @Column(nullable = false, length = 255)
    private String dValue;

    /**
     * 说明
     * @return
     */
    @Column
    private String introduction;
}
