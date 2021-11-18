package com.meehoo.biz.common.io;

import com.google.gson.Gson;

/**
 * @author zc
 * @date 2020-01-20
 */
public class JsonUtil {
    private static Gson gson;

    private static Gson getGson(){
        if (gson == null){
            synchronized (Gson.class){
                if (gson == null){
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    public static String toJson(Object object){
        return getGson().toJson(object);
    }

    public static <T> T fromJson(String json,Class<T> tClass){
        return getGson().fromJson(json,tClass);
    }
}
