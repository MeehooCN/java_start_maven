package com.meehoo.biz.core.basic.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by 汪涛 on 2015/12/22.
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
public class SearchCondition {
    public static final String ORDER_BY_DESC = "orderByDesc";
    public static final String ORDER_BY_ASC  = "orderByASC";
    public static final String IS_NULL  = "isNull";
    public static final String IS_NOT_NULL  = "isNotNull";

    private String name;
    private String operand;
    private String value;
}
