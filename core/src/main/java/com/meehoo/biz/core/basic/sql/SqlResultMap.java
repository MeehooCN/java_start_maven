package com.meehoo.biz.core.basic.sql;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zc
 * @date 2020-09-14
 */
public class SqlResultMap extends HashMap<String,Object> {
    public SqlResultMap() {
    }

    public SqlResultMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SqlResultMap(Map<? extends String, ?> m) {
        super(m);
    }

    public int getInt(String key){
        key = filtKey(key);
        Object o = super.get(key);
        if (o!=null){
            return SqlUtil.objectToInt(o);
        }
        return 0;
    }

    public Integer getInteger(String key){
        key = filtKey(key);
        Object o = super.get(key);
        if (o!=null){
            return SqlUtil.objectToInt(o);
        }
        return null;
    }

    public double getDouble(String key){
        key = filtKey(key);
        Object o = super.get(key);
        if (o!=null){
            return SqlUtil.objectToDouble(o);
        }
        return 0;
    }

    public String getString(String key){
        key = filtKey(key);
        Object o = super.get(key);
        if (o!=null){
            return SqlUtil.objectToString(o);
        }
        return "";
    }

    public Date getDate(String key){
        key = filtKey(key);
        Object o = super.get(key);
        if (o!=null){
            return SqlUtil.objectToDate(o);
        }
        return null;
    }

    public int getCount(){
        return getInt(SQLHelper.Result_Count);
    }

    public double getSum(){return getDouble(SQLHelper.Result_Sum);}

    public String getDate(){return getString(SQLHelper.Result_Date);}

    private String filtKey(String key){
        int index;
        if ((index=key.indexOf("."))!=-1){
            key = key.substring(index+1,key.length());
        }
        return key;
    }
}
