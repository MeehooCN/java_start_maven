package com.meehoo.biz.core.basic.vo.setting;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@Data
public class CitySelectVO {
    private String label;
    private String value;
    private List<AreaSelectVO> children;

    public CitySelectVO() {
        this.children = new ArrayList<>();
    }

    public CitySelectVO(List<AreaSelectVO> children) {
        this.children = children;
    }
}
