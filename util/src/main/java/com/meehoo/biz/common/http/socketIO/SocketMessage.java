package com.meehoo.biz.common.http.socketIO;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by CZ on 2018/10/8.
 */
public class SocketMessage {
    /**
     * 消息事件名，必须赋值
     */
    private String event;

    /**
     * 消息消费者，必须赋值，一般为userId
     */
    private List<String> consumers;

    /**
     * 消息内容，必须赋值
     */
    private Object data;

    /**
     * 过期时间，不赋值表示永不过期
     */
    private Date expiryTime;

    public SocketMessage(String event, String consumer, Object data) {
        this(event, consumer, data, null);
    }

    public SocketMessage(String event, String consumer, Object data, Date expiryTime) {
        this(event, Collections.singletonList(Objects.requireNonNull(consumer)), data, expiryTime);
    }

    public SocketMessage(String event, List<String> consumers, Object data) {
        this(event, consumers, data, null);
    }

    public SocketMessage(String event, List<String> consumers, Object data, Date expiryTime) {
        if (consumers == null || consumers.isEmpty()) {
            throw new NullPointerException("消息消费者不能为空");
        }
        this.event = Objects.requireNonNull(event);
        this.consumers = consumers;
        this.data = Objects.requireNonNull(data);
        this.expiryTime = expiryTime;
    }

    public String getEvent() {
        return event;
    }

    public SocketMessage setEvent(String event) {
        this.event = Objects.requireNonNull(event);
        return this;
    }

    public List<String> getConsumers() {
        return consumers;
    }

    public SocketMessage setConsumers(List<String> consumers) {
        this.consumers = consumers;
        return this;
    }

    public Object getData() {
        return data;
    }

    public SocketMessage setData(Object data) {
        this.data = Objects.requireNonNull(data);
        return this;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public SocketMessage setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }
}
