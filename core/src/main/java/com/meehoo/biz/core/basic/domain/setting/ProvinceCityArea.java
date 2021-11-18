package com.meehoo.biz.core.basic.domain.setting;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by DuYong on 2017/9/1.
 *
 * 省市区
 */
@Entity
@Table(name="sys_pro_city_area")
@DynamicInsert
@DynamicUpdate
public class ProvinceCityArea {

    public static final String CODE_AREA_EMPTY = "a00";  //a00表示区编码是空
    public static final String CODE_CITY_EMPTY = "c00";  //c00表示市编码是空


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @Column(length = 100, nullable = false)
    private String name;
    /**
     * 省级代码
     */
    @Column(length = 20)
    private String provinceCode;
    /**
     * 市级代码
     */
    @Column(length = 20)
    private String cityCode;
    /**
     * 区县级代码
     */
    @Column(length = 20)
    private String areaCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
