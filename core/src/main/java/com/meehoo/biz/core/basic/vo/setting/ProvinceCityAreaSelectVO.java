package com.meehoo.biz.core.basic.vo.setting;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@Data
public class ProvinceCityAreaSelectVO {
    private List<ProvinceSelectVO> provinceSelectVOList;

    public ProvinceCityAreaSelectVO() {
        this.provinceSelectVOList = new ArrayList<>();
    }

    public ProvinceCityAreaSelectVO(List<ProvinceSelectVO> list) {
        this.provinceSelectVOList = list;
    }

    public List<ProvinceSelectVO> getProvinceSelectVOList() {
        return provinceSelectVOList;
    }

    public void setProvinceSelectVOList(List<ProvinceSelectVO> list) {
        this.provinceSelectVOList = list;
    }
}
