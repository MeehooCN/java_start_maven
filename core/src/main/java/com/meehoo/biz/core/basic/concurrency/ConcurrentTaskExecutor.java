package com.meehoo.biz.core.basic.concurrency;

import com.meehoo.biz.core.basic.concurrency.task.ITask;
import com.meehoo.biz.core.basic.concurrency.task.TaskResult;
import com.meehoo.biz.core.basic.concurrency.task.pic.PicTaskBuilder;
import org.springframework.stereotype.Component;

/**
 * 暴露出来，用作并发任务执行
 * @author zc
 * @date 2020-12-26
 */
@Component
public class ConcurrentTaskExecutor {
    private final ConcurrentManager manager;

    public ConcurrentTaskExecutor() {
        manager = new ConcurrentManager();
        manager.initial();
    }

    public PicTaskBuilder PIC(){
        return new PicTaskBuilder(this);
    }
    // 执行任务
    public <T> void execute(ITask<T> require){
        // 执行
        manager.exe(require);
    }
    // 获得任务结果
    public TaskResult getProcess(String taskId){
        return manager.getInfo(taskId);
    }

//    private String parseResult(List results){
//        StringBuffer sb = new StringBuffer();
//        results.forEach(e->sb.append(e.toString()).append("\n"));
//        return sb.toString();
//    }
}
