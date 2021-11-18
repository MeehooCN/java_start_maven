package com.meehoo.biz.core.basic.concurrency.handle;

/**
 * @author zc
 * @date 2021-01-04
 */
public abstract class AbstractHandler<T> implements IHandler{
//    private final ConcurrentManager configure;
//
//    public AbstractHandler(ConcurrentManager configure) {
//        this.configure = configure;
//    }
//
//    @Override
//    public void uploadProcess(String taskId,Boolean successOrFail) {
//        if (successOrFail){
//            configure.getTaskInfoCache().success(taskId);
//        }else{
//            configure.getTaskInfoCache().fail(taskId);
//        }
//    }
//
//
//    @Override
//    public void exe(ITask task) {
//        try {
//            exe(task);
//            uploadProcess(task.getId(),true);
//        }catch (Exception e){
//            uploadProcess(task.getId(),false);
//        }
//    }
//
//    abstract void doExe(T source) throws Exception;
}
