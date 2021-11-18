package com.meehoo.biz.core.basic.vo.common;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.parent.AddressEntity;
import com.meehoo.biz.core.basic.vo.BaseEntityVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by CZ on 2018/1/23.
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class AddressEntityVO extends BaseEntityVO {
    /**
     * 省
     */
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

    protected AddressEntityVO(AddressEntity addressEntity) {
        super(addressEntity);
        this.city = addressEntity.getCity();
        this.cityCode = addressEntity.getCityCode();
        this.province = addressEntity.getProvince();
        this.provinceCode = addressEntity.getProvinceCode();
        this.area = addressEntity.getArea();
        this.areaCode = addressEntity.getAreaCode();
        this.address = addressEntity.getAddress();
    }

    /**
     * 详细地址带省市区
     */
    public String getDetailAddress(){
        String detailAddress = "";
        if (StringUtil.stringNotNull(province)){
            detailAddress+=province+"/";
        }
        if (StringUtil.stringNotNull(city)){
            detailAddress+=city+"/";
        }
        if (StringUtil.stringNotNull(area)){
            detailAddress+=area+"/";
        }
        if (StringUtil.stringNotNull(address)){
            detailAddress+=address;
        }
        return detailAddress;
    }
}