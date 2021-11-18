package com.meehoo.biz.core.basic.concurrency.task;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zc
 * @date 2020-12-31
 */
//@Setter
@Getter
public class TaskResult {
    private AtomicInteger successQty;
    private AtomicInteger failedQty;

    public TaskResult() {
        this.successQty = new AtomicInteger();
        this.failedQty = new AtomicInteger();
    }

    public void success(){
        successQty.incrementAndGet();
    }

    public void fail(){
        failedQty.incrementAndGet();
    }

    public String getDescription(){
        return "成功"+successQty.get()+"个;失败"+failedQty.get()+"个";
    }
}
