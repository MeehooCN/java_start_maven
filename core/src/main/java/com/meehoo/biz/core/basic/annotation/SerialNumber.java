package com.meehoo.biz.core.basic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编号字段注解
 * Created by CZ on 2018/1/15.
 * @see SetBySystem
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerialNumber{
}
