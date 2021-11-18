package com.meehoo.biz.core.basic.ro.bos;

import com.meehoo.biz.core.basic.ro.BaseEntityRO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by CZ on 2018/1/22.
 */
@Getter
@Setter
public class AddressEntityRO extends BaseEntityRO {

    /**
     * 省市区code
     */
//    @ApiModelProperty("省市区code")
//    protected List<String> provinceCityArea;
    protected String provinceCode;
    protected String province;

    /**
     * 市
     */
    protected String cityCode;

    protected String city;

    /**
     * 区
     */
    protected String areaCode;

    protected String area;

    /**
     * 地址
     */
    protected String address;
}