package com.meehoo.biz.core.basic.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口统一返回值
 * @author zc
 * @date 2019-07-09
 */
@Data
public final class HttpResult<T> implements Serializable{
    public static final int Flag_Success = 0;
    public static final int Flag_Fail= 1;
    public static final int Flag_NotLogin = 4004;
    public static final int Flag_HasCustomer = 2;
    public static final int Flag_PwdWrong= 3;

    private T data;
//    private Throwable ex;
    private String msg;
    private int flag;

    HttpResult() {
        msg = "成功";
        flag = Flag_Success;
    }

    HttpResult(T data) {
        this.data = data;
        msg = "成功";
        flag = Flag_Success;
    }

    HttpResult(Throwable ex) {
        this.msg = ex.getMessage();
        flag = Flag_Fail;
    }

    public static <T> HttpResult<T> success(T data){
        return new HttpResult<>(data);
    }

    public static HttpResult<String> success(){
        return new HttpResult<>();
    }

    public static HttpResult<String> fail(Throwable ex){
        return new HttpResult<>(ex);
    }

    public static HttpResult<String> fail(String reason){
        HttpResult<String> result = new HttpResult<>();
        result.setFlag(Flag_Fail);
        result.setMsg(reason);
        return result;
    }
}
