package com.meehoo.biz.core.basic.concurrency.handle;


/**
 * @author zc
 * @date 2020-12-26
 */
public interface IHandler<T>{
    void exe(T source) throws Exception;
}
