package com.meehoo.biz.core.basic.enumeration;

/**
 * 字段类型
 * Created by CZ on 2018/1/8.
 */
public enum FieldType {
    NUMBER,//编号字段
    OPERATOR,//操作员字段
    SETBYSYS,//后台赋值字段
    COLUMN,// 一般列
    FOREIGN,//外键
    COLLECTION//集合
}
