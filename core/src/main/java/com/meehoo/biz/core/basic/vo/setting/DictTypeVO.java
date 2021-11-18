package com.meehoo.biz.core.basic.vo.setting;


import com.meehoo.biz.core.basic.domain.setting.DictType;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Data;

/**
 * Created by Administrator on 2017/11/02.
 */
@Data
public class DictTypeVO extends IdEntityVO{
    private String id;
    private String module;
    private String name;
    private String code;
    private int isSysSet;
    private String isSysSetShow;

    public DictTypeVO(DictType dictType) throws Exception {
        this.id = dictType.getId();
        this.module = dictType.getModule();
        this.code = dictType.getCode();
        this.name = dictType.getName();
        this.isSysSet = dictType.getIsSysSet();
        this.isSysSetShow = isSysSet == DictType.ISSYSSET_YES ? "是" : "否";
    }
}
