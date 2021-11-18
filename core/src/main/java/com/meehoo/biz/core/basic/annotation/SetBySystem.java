package com.meehoo.biz.core.basic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 由后台赋值的普通字段,如创建时间、更新时间
 * Created by CZ on 2018/1/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SetBySystem {

    /**
     * 是否只在新增时初始化字段,该属性与updateOnly不能同时为true
     */
    boolean createOnly() default false;

    /**
     * 是否只在更新时初始化字段，该属性与createOnly不能同时为true
     */
    boolean updateOnly() default false;

    /**
     * 初始化时默认的值，如设置初始密码
     * 如果默认为newInstance将使用无参构造方法初始化
     */
    String defValue() default "newInstance";
}
