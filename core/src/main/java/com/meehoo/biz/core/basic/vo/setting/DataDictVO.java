package com.meehoo.biz.core.basic.vo.setting;


import com.meehoo.biz.core.basic.domain.setting.DataDict;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjian on 2016/10/28.
 */
@Getter
@Setter
public class DataDictVO {
    private DataDict dataDict;

    public DataDictVO(){
        this.dataDict = new DataDict();
    }

    public DataDictVO(DataDict dataDict){
        this.dataDict = dataDict;
    }

    public String getId(){return this.dataDict.getId().toString();}

    public String getDType(){return this.dataDict.getDType().toString();}

    public String getType(){return  this.dataDict.getDType().toString();}

    public String getDCode(){return this.dataDict.getDCode();}

    public String getCode(){return this.dataDict.getDCode();}

    public String getDValue(){return this.dataDict.getDValue();}

    public String getValue(){
        return this.dataDict.getDValue();
    }

    public String getIntroduction() {
        return this.dataDict.getIntroduction();
    }
}
