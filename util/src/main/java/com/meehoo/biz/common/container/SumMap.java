package com.meehoo.biz.common.container;

import java.util.HashMap;

/**
 * @author zc
 * @date 2020-07-10
 */
public class SumMap<T> extends HashMap<T,Integer>{
    public SumMap() {
        super();
    }

    public SumMap(int initialCapacity) {
        super(initialCapacity);
    }

    public void put(T key){
        Integer qty = getOrDefault(key, 0);
        put(key,qty+1);
    }
}
