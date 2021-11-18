package com.meehoo.biz.core.basic.vo.setting;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@Data
public class ProvinceSelectVO {
    private String label;
    private String value;
    private List<CitySelectVO> children;

    public ProvinceSelectVO() {
        this.children = new ArrayList<>();
    }

    public ProvinceSelectVO(List<CitySelectVO> children) {
        this.children = children;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<CitySelectVO> getChildren() {
        return children;
    }

    public void setChildren(List<CitySelectVO> children) {
        this.children = children;
    }
}
