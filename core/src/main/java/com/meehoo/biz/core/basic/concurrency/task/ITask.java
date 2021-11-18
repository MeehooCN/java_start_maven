package com.meehoo.biz.core.basic.concurrency.task;

import java.util.List;

/**
 * @author zc
 * @date 2020-12-26
 */
public interface ITask<T> {
    List<T> getResource();

    String getId();

    String getName();
}
