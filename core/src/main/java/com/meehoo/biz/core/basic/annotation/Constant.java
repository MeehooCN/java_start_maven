package com.meehoo.biz.core.basic.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 常量描述
 * @author zc
 * @date 2020-08-18
 */
@Documented
@Target({TYPE,FIELD})
@Retention(RUNTIME)
public @interface Constant {
    String value() default "";
}
