package com.meehoo.biz.core.basic.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 域模型编号注解
 * Created by CZ on 2018/12/5.
 * @see SerialNumber
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface CodeEntity {
    /**
     * id,不赋值自动生成
     *
     * @return
     */
    long id() default -1L;

    /**
     * 编号前缀{@link SerialNumber#prefix}，如ZZJG(组织机构)
     *
     * @return
     */
    String value();

    /**
     * 编号中间填充字符串
     *
     * @return
     */
    String placeHolder() default "000";

    /**
     * 编号初始序号
     *
     * @return
     */
    long firstSeq() default 0L;
}
