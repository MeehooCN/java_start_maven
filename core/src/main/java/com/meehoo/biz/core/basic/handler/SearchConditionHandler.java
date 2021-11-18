package com.meehoo.biz.core.basic.handler;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.core.basic.exception.SearchConditionException;
import com.meehoo.biz.core.basic.param.SearchCondition;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Component;


/**
 * @author zc
 * @date 2020-01-09
 */
@Component
public class SearchConditionHandler {
    public Object transform(String value,String fieldType){
        switch (fieldType) {
            case "String":
                return value;
            case "Long":
            case "long":
                return Long.valueOf(value);
            case "Integer":
            case "int":
                return Integer.parseInt(value);
            case "Date":
                return DateUtil.stringToDate(value);
        }
        return null;
    }

    public void handle(String name,Object value,String operand,DetachedCriteria criteria) throws SearchConditionException{
        try {
            if (operand.equals(SearchCondition.ORDER_BY_ASC)){
                criteria.addOrder(Order.asc(name));
            }else if (operand.equals(SearchCondition.ORDER_BY_DESC)){
                criteria.addOrder(Order.desc(name));
            }
            else if (SearchCondition.IS_NULL.equalsIgnoreCase(operand)){
                criteria.add(Restrictions.isNull(name));
            }
            else if (SearchCondition.IS_NOT_NULL.equalsIgnoreCase(operand)){
                criteria.add(Restrictions.isNotNull(name));
            }
            else if (BaseUtil.objectNotNull(value)){
                switch (operand) {
                    case "like":
                        criteria.add(Restrictions.like(name, (String)value, MatchMode.ANYWHERE));
                        break;
                    case "equal":
                    case "=":
                        criteria.add(Restrictions.eq(name, value));
                        break;
                    case "!=":
                    case "<>":
                        criteria.add(Restrictions.ne(name, value));
                        break;
                    case ">":
                    case "gt":
                        criteria.add(Restrictions.gt(name, value));
                        break;
                    case "<":
                    case "lt":
                        criteria.add(Restrictions.lt(name, value));
                        break;
                    case "in":
                        criteria.add(Restrictions.in(name, ((String)value).split(",")));
                        break;
                    default:
                        break;
                }
            }
        }catch (Exception e){
            throw new SearchConditionException("条件不对");
        }
    }

}
