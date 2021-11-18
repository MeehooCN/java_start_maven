package com.meehoo.biz.core.basic.util;

import com.meehoo.biz.core.basic.annotation.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zc
 * @date 2020-09-02
 */
public class ConstantUtil {
    public static Map<String,Map<Integer,String>> cache = new HashMap<>(50);

    public static Map<Integer,String> getConstantStr(Class tClass, String fieldName){
        String key = tClass.getName() + "."+fieldName;
        Map<Integer,String> result;
        if (cache.containsKey(key)){
            result = cache.get(key);
        }else{
            result = new HashMap<>();

            Field[] fields = tClass.getFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())&&Modifier.isPublic(field.getModifiers())
                        &&field.getName().toLowerCase().startsWith(fieldName.toLowerCase())){
                    if (field.getType() == Integer.class||field.getType() == int.class){
                        try {
                            int anInt = field.getInt(tClass);

                            Constant annotation = field.getAnnotation(Constant.class);
                            if (annotation!=null){
                                String value = annotation.value();
                                result.put(anInt,value);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (result.size()>0){
                cache.put(key,result);
            }
        }
        return result;
    }

    public static String transConstant(Class tClass, String fieldName,int value){
        Map<Integer, String> map = getConstantStr(tClass, fieldName);
        return map.getOrDefault(value,"");
    }
}
