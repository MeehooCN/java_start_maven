package com.meehoo.biz.core.basic.vo;

import com.meehoo.biz.core.basic.sql.SqlUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 饼图的正取返回格式
 * @author zc
 * @date 2020-05-28
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieChartVO {
    private String name;//

    private Double value;//

    public PieChartVO(String name){
        this.name = name;
        value = 0d;
    }

    public PieChartVO(Object[] objects){
        if (objects.length!=2){
            throw new RuntimeException("结果长度不对");
        }
        Object o1 = objects[0];
        Object o2 = objects[1];
        if (o1 instanceof String){
            this.name = SqlUtil.objectToString(o1);
            this.value = SqlUtil.objectToDouble(o2);
        }else{
            this.name = SqlUtil.objectToString(o2);
            this.value = SqlUtil.objectToDouble(o1);
        }
    }
}
