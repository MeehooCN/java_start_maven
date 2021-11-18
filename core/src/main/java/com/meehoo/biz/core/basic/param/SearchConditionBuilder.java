package com.meehoo.biz.core.basic.param;

import com.meehoo.biz.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zc
 * @date 2020-04-20
 */
public class SearchConditionBuilder {
    private List<SearchCondition> searchCondition;

    public SearchConditionBuilder(){
        searchCondition = new ArrayList<>();
    }

    public SearchConditionBuilder add(String name, String operand, String value){
        if (StringUtil.stringNotNull(name)&& StringUtil.stringNotNull(value)){
            searchCondition.add(new SearchCondition(name, operand, value));
        }
        return this;
    }

    public SearchConditionBuilder addEq(String name, String value){
        return add(name,"=",value);
    }

    public SearchConditionBuilder addIn(String name, List<String> values){
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value);
            sb.append(",");
        }
        return add(name,"in",sb.toString());
    }

    public SearchConditionBuilder addGe(String name, String value){
        return add(name,">=",value);
    }

    public SearchConditionBuilder addLe(String name, String value){
        return add(name,"<=",value);
    }

    public SearchConditionBuilder addLikeAny(String name, String value){
        if (StringUtil.stringNotNull(value))
            return add(name,"like","%"+value+"%");
        return this;
    }

    public SearchConditionBuilder addLikeStart(String name, String value){
        if (StringUtil.stringNotNull(value))
            return add(name,"like","%"+value);
        return this;
    }

    public SearchConditionBuilder addOrderDesc(String name){
        if (StringUtil.stringNotNull(name)){
            return add(name,SearchCondition.ORDER_BY_DESC,"");
        }
        return this;
    }

    public SearchConditionBuilder addOrderAsc(String name){
        if (StringUtil.stringNotNull(name)){
            return add(name,SearchCondition.ORDER_BY_ASC,"");
        }
        return this;
    }

    public SearchConditionBuilder addOrder(int type,String name){
        if (type>0)
            return addOrderAsc(name);
        else if (type<0)
            return addOrderDesc(name);
        return this;
    }

    public List<SearchCondition> toList(){
        return searchCondition;
    }
}
