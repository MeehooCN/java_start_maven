package com.meehoo.biz.core.basic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作员字段注解
 * Created by CZ on 2018/1/15.
 * @see SetBySystem
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operator {

    boolean isNumber() default false;  //是否是编号字段，不能与isName同时为true

    boolean isName() default false;  //是否是姓名字段，不能与isNumber同时为true
}