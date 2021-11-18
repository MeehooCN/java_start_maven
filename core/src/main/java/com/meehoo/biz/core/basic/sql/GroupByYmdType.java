package com.meehoo.biz.core.basic.sql;

/**
 * @author zc
 * @date 2020-09-11
 */
public enum GroupByYmdType {
    Y,YMD,YM,WEEK;


    public static final int TimeDim_Y = 1; // 按年统计
    public static final int TimeDim_YM = 2; // 按月统计
    public static final int TimeDim_WEEK = 3; // 按周统计
    public static final int TimeDim_YMD = 4; // 按天统计
    public static GroupByYmdType of(int timeDim){
        if (timeDim == TimeDim_YMD){
            return YMD;
        }else if (timeDim == TimeDim_YM){
            return YM;
        }else if (timeDim == TimeDim_WEEK){
            return WEEK;
        }else if (timeDim == TimeDim_Y){
            return Y;
        }else{
            throw new RuntimeException("时间维度不匹配");
        }
    }
}
