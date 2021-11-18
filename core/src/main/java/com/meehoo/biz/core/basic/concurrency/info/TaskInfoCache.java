package com.meehoo.biz.core.basic.concurrency.info;


import com.meehoo.biz.core.basic.concurrency.task.TaskResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务执行信息管理
 * @author zc
 * @date 2020-12-31
 */
public final class TaskInfoCache {
    private final Map<String,TaskResult> task_info ;

    {
        task_info = new ConcurrentHashMap<>();
    }

    public void success(String taskId){
        if (task_info.containsKey(taskId)){
            task_info.get(taskId).success();
        }else{
            TaskResult taskResult = new TaskResult();
            taskResult.success();
            task_info.put(taskId,taskResult);
        }
    }

    public void fail(String taskId){
        if (task_info.containsKey(taskId)){
            task_info.get(taskId).fail();
        }else{
            TaskResult taskResult = new TaskResult();
            taskResult.fail();
            task_info.put(taskId,taskResult);
        }
    }

//    public void putInfo(String taskId,String info){
//        if (task_info.containsKey(taskId)){
//            task_info.get(taskId).add(info);
//        }else{
//            List<String> list = new CopyOnWriteArrayList<>();
//            list.add(info);
//            task_info.put(taskId,list);
//        }
//    }
//
    public TaskResult getInfo(String taskId){
        return task_info.get(taskId);
    }
}
