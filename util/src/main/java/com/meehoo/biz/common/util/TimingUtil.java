package com.meehoo.biz.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zc
 * @date 2019-10-10
 */
public class TimingUtil {
    private static List<Long> timeList;

    public static void start(){
        if (timeList == null){
            timeList = new ArrayList<>();
        }else {
            timeList.clear();
        }
        timeList.add(System.currentTimeMillis());
    }

    public static void count(){
        timeList.add(System.currentTimeMillis());
    }

    public static void printf(){
        for (int i=1;i<timeList.size();i++){
            System.out.println("第"+i+"阶段耗时"+BigDecimalUtil.div(timeList.get(i)-timeList.get(i-1),1000)+"秒");
        }
    }
}
