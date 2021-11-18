package com.meehoo.biz.core.basic.enumeration;

/**
 * 公共方法名枚举
 * Created by CZ on 2018/1/5.
 */
public enum CommonMethod {
    GET_ID("getId"),
    GET_NAME("getName"),
    GET_NUMBER("getNumber"),
    GET_BILLNO("getBillNo"),
    GET_STATUS("getStatus"),
    GET_CURRUSERID("getCurrUserId"),
    SET_OPERATORANDDATE("setOperatorAndDate"),
    SET_NUMBER("setNumber"),
    SET_BILLNO("setBillNo"),
    FIELDSCHECK("checkFields");

    private String name;

    CommonMethod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}

