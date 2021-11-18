package com.meehoo.biz.core.basic.vo.setting;


import com.meehoo.biz.core.basic.domain.setting.DictValue;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Data;

/**
 * Created by Administrator on 2017/11/02.
 */
@Data
public class DictValueVO extends IdEntityVO{
    private String id;
    private String mkey;
    private String mvalue;
    private String dictTypeId;
    private String dictTypeNumber;
    private String dictTypeName;
    private int isSysSet;
    private String isSysSetShow;

    public DictValueVO(DictValue dictValue){
        parseFromDictValue(dictValue);
    }

    public DictValueVO(Object[] sqlRow){
        if(sqlRow!=null&&sqlRow.length>0){
            for (Object obj : sqlRow) {
                if(obj instanceof DictValue){
                    parseFromDictValue((DictValue)obj);
                    break;
                }
            }
        }
    }

    private void parseFromDictValue(DictValue dictValue){
        this.id = dictValue.getId();
        this.mkey = dictValue.getMkey();
        this.mvalue = dictValue.getMvalue();
        this.dictTypeId = dictValue.getDictType().getId();
        this.dictTypeNumber = dictValue.getDictType().getCode();
        this.dictTypeName = dictValue.getDictType().getName();
        this.isSysSet = dictValue.getIsSysSet();
        this.isSysSetShow = isSysSet == DictValue.ISSYSSET_YES ? "是" : "否";
    }
}
