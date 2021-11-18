package com.meehoo.biz.core.basic.concurrency;

import com.meehoo.biz.core.basic.concurrency.handle.IHandler;
import com.meehoo.biz.core.basic.concurrency.handle.PicUploadHandler;
import com.meehoo.biz.core.basic.concurrency.handle.TaskHandleAdapter;
import com.meehoo.biz.core.basic.concurrency.info.TaskInfoCache;
import com.meehoo.biz.core.basic.concurrency.task.IBuilder;
import com.meehoo.biz.core.basic.concurrency.task.ITask;
import com.meehoo.biz.core.basic.concurrency.task.TaskResult;
import com.meehoo.biz.core.basic.concurrency.task.pic.PicTask;
import com.meehoo.biz.core.basic.concurrency.task.pic.PicTypeEnum;
import com.meehoo.biz.core.basic.concurrency.thread.ExecutorManager;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 并发配置管理
 * @author zc
 * @date 2020-12-30
 */
//@Component
@Getter
public final class ConcurrentManager {
    private final ExecutorManager executorManager;
    private final TaskHandleAdapter taskHandleAdapter;
    private final Map<String,IBuilder> builderMap;
    private final TaskInfoCache taskInfoCache;

    {
        executorManager = new ExecutorManager();
        taskHandleAdapter = new TaskHandleAdapter();
        builderMap = new ConcurrentHashMap<>();
        taskInfoCache = new TaskInfoCache();
    }
    void initial(){
//        executorManager.setTaskHandleAdapter(taskHandleAdapter);

        taskHandleAdapter.registHandler(PicTask.class.getSimpleName()+":"+PicTypeEnum.upload,new PicUploadHandler());
//        taskHandleAdapter.registHandler(PicTask.class.getSimpleName()+":"+PicTypeEnum.download,new PicDownloadHandler(this));
    }

    <T> void exe(ITask<T> task){
        // 执行handler
        IHandler<T> handler = taskHandleAdapter.adapter(task);
        Assert.notNull(handler,"没有对应的处理器");
        // 任务源
        List<T> resource = task.getResource();
        // 执行
        for (T t : resource) {
            executorManager.exeRunnable(()->{
                try {
                    handler.exe(t);
                    uploadProcess(task.getId(),true);
                } catch (Exception e) {
                    e.printStackTrace();
                    uploadProcess(task.getId(),false);
                }
            });
        }
    }

    private void uploadProcess(String taskId,Boolean successOrFail) {
        if (successOrFail){
            taskInfoCache.success(taskId);
        }else{
            taskInfoCache.fail(taskId);
        }
    }

    TaskResult getInfo(String taskId){
        return taskInfoCache.getInfo(taskId);
    }


    // 注册任务和处理器
//    public void register(String taskName, IHandler iHandler){
//        taskHandleAdapter.registHandler(taskName,iHandler);
//    }
}
