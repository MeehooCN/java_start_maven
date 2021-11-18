package com.meehoo.biz.core.basic.concurrency.task.pic;

import com.meehoo.biz.core.basic.concurrency.task.ITask;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * 需求
 * @author zc
 * @date 2020-12-26
 */
@Setter
@Getter
public class PicTask implements ITask<String> {
    private List<String> url;
    private PicTypeEnum requireType;
    private final String id;

    public PicTask() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public List<String> getResource() {
        return url;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return PicTask.class.getSimpleName()+":"+requireType;
    }
}
