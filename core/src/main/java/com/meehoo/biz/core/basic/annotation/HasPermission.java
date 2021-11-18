package com.meehoo.biz.core.basic.annotation;

import java.lang.annotation.*;

/**
 * @author zc
 * @date 2020-05-07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasPermission{
    String value() default "";
}
