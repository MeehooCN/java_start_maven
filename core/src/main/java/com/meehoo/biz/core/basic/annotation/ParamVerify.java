package com.meehoo.biz.core.basic.annotation;

import java.lang.annotation.*;

/**
 * 方法是否需要参数验证
 * @author zc
 * @date 2019-11-18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamVerify {
}
