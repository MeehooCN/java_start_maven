package com.meehoo.biz.core.basic.vo.setting;


import com.meehoo.biz.core.basic.domain.setting.SystemParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/11/08.
 */
@Getter
@Setter
public class SystemParamVO {
    private String id;
    private String number;
    private String name;
    private String paramValue;
    private String paramDesc;

    public SystemParamVO(SystemParam systemParam){
        this.id = systemParam.getId();
        this.number = systemParam.getCode();
        this.name = systemParam.getName();
        this.paramValue = systemParam.getParamValue();
        this.paramDesc = systemParam.getParamDesc();
    }
}
