package com.meehoo.biz.core.basic.concurrency.task.pic;

import com.meehoo.biz.core.basic.concurrency.ConcurrentTaskExecutor;
import com.meehoo.biz.core.basic.concurrency.task.IBuilder;

import java.util.ArrayList;

/**
 * @author zc
 * @date 2020-12-29
 */
public class PicTaskBuilder implements IBuilder {
    private PicTask picRequire ;
    private final ConcurrentTaskExecutor concurrentTaskExecutor;

    public PicTaskBuilder( ConcurrentTaskExecutor concurrentTaskExecutor) {
        this.picRequire = new PicTask();
        picRequire.setUrl(new ArrayList<>());
        this.concurrentTaskExecutor = concurrentTaskExecutor;
    }

    public PicTaskBuilder source(String source){
        picRequire.getUrl().add(source);
        return this;
    }

    public PicTaskBuilder source(String... source){
        for (String s : source) {
            picRequire.getUrl().add(s);
        }
        return this;
    }

    public PicTaskBuilder type(PicTypeEnum requireType){
        picRequire.setRequireType(requireType);
        return this;
    }

    public String exeForTaskId(){
        concurrentTaskExecutor.execute(picRequire);
        return picRequire.getId();
    }

//    public String exe(){
//        return concurrentTaskExecutor.execute(picRequire);
//    }
}
