package com.meehoo.biz.core.basic.concurrency.handle;


import com.meehoo.biz.core.basic.concurrency.task.ITask;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务处理适配器
 * @author zc
 * @date 2020-12-26
 */
public class TaskHandleAdapter {
    private ConcurrentHashMap<String,IHandler> name_handler; // 根据需求类型选择handler

    public TaskHandleAdapter() {
        this.name_handler = new ConcurrentHashMap<>();
    }

    public void registHandler(String taskName,IHandler iHandler){
        name_handler.put(taskName,iHandler);
    }

    public <T> IHandler<T> adapter(ITask<T> require){
        String simpleName = require.getName();
        if (name_handler.containsKey(simpleName)){
            return name_handler.get(simpleName);
        }
        throw new RuntimeException("无法适配改任务类型");
    }
}
