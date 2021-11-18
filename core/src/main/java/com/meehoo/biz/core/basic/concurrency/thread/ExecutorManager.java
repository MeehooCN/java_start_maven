package com.meehoo.biz.core.basic.concurrency.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 多线程并发工具
 * @author zc
 * @date 2020-07-21
 */
//@Component
public class ExecutorManager {
    private static final int SleepTime = 2000;
    private ExecutorService executor;
//    private TaskHandleAdapter taskHandleAdapter;

    public ExecutorManager() {
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        int nThread = Runtime.getRuntime().availableProcessors();
//        executor = new ThreadPoolExecutor(nThread,nThread*2,1000L,TimeUnit.MILLISECONDS,queue);
//        taskHandleAdapter = new TaskHandleAdapter();
    }

//    public void setTaskHandleAdapter(TaskHandleAdapter adapter){
//        this.taskHandleAdapter = adapter;
//    }

    public void exeFuture(FutureTask task){
        executor.execute(task);
    }

    public void exeRunnable(Runnable runnable){
        executor.submit(runnable);
    }

//    public <T> void batchFunction(ITask<T> require){
//        Assert.notNull(taskHandleAdapter,"adapter不能为空");
//        IHandler iHandler = taskHandleAdapter.adapter(require);
//        iHandler.exe(require);
//    }

//    private  <T> void consumeAsyn(List<T> source, Consumer<T> consumer){
//        for (T t : source) {
//            executor.submit(()->consumer.accept(t));
//        }
//    }

//    public <T,V> void batchFunction(List<T> source, IHandler<T,V> handler){
//        // 转成任务
//        List<FutureTask> futureTasks = source.stream().map(e -> {
//            FutureTask futureTask = new FutureTask(() -> handler.apply(e));
//            // 执行任务
//            executor.execute(futureTask);
//            return futureTask;
//        }).collect(Collectors.toList());
//    }

//    private  <T,V> List<V> batchFunctionWithAbandon(List<T> source, Function<T,V> consumer){
////        long nowMilis = System.currentTimeMillis();
//
//        List<FutureTask<V>> taskList = new ArrayList<>(source.size());
//        for (T t : source) {
//            Callable<V> callable = new Callable<V>() {
//                @Override
//                public V call() throws Exception {
//                    return consumer.apply(t);
//                }
//            };
//            FutureTask<V> task = new FutureTask<>(callable);
//            taskList.add(task);
//            exeFuture(task);
//        }
//
//        List<V> resultList = new ArrayList<>(source.size());
//        List<FutureTask<V>> undoneList = new ArrayList<>();
//        for (FutureTask<V> future : taskList) {
//            try {
//                if(future.isDone()){
//                    V e = future.get();
//                    resultList.add(e);
//                }else{
//                    undoneList.add(future);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (resultList.size()<source.size()){
//            try {
//                Thread.sleep(SleepTime);
//
//                for (FutureTask<V> future : undoneList) {
//                    if(future.isDone()){
//                        V e = future.get();
//                        resultList.add(e);
//                    }else{
//                        future.cancel(true);
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        return resultList;
//    }
}
