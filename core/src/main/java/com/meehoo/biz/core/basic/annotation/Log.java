package com.meehoo.biz.core.basic.annotation;

import java.lang.annotation.*;

/**
 * Created by zc on 2018/1/5 0005.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String value() default "日志记录开始";

    int paramNum() default 0; // 是否有参数，0 没有  大于0的是具体参数的排序

    int paramNum1() default 0; // 是否有参数，0 没有  大于0的是具体参数的排序

//    int paramNum2() default 0; // 是否有参数，0 没有  大于0的是具体参数的排序
}
