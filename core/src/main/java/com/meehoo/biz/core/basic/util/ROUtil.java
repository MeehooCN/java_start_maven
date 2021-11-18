package com.meehoo.biz.core.basic.util;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by qx on 2019/5/7.
 */
public class ROUtil {

    private final static String METHOD_SETTER = "set";//set方法
    private final static String METHOD_GETTER = "get";//get方法

    /**
     * ro转换成domain(相同字段名赋值)
     * 以ro中的字段为基准
     */
    public static <RO, D> D convertRoToDomain(RO ro, Class<D> domainClass) throws Exception {
        return convertRoToDomain(ro,domainClass.newInstance());
    }

    /**
     * 将ro中不为空的字段赋值给doamin
     * @param ro
     * @param entity
     * @return
     * @throws Exception
     */
    public static <RO, D> D convertRoToDomain(RO ro, D entity) throws Exception {
        Map<String, Method> map = getMethodByNotNullField(ro);
        Map<String, Method> entityMap = convertMethodsToMap(entity.getClass(), METHOD_SETTER);
        for (Map.Entry<String, Method> entry : map.entrySet()) {
            Method set = entityMap.get(replaceString(entry.getKey(), "s", 0));
            if(set!=null){
                set.invoke(entity,entry.getValue().invoke(ro));
            }
        }
        return entity;
    }

    /**
     * 获取对象字段中不为空的getter方法
     * @param t
     * @return
     */
    public static <T> Map<String,Method> getMethodByNotNullField(T t) throws Exception {
        Class searchClass = t.getClass();
        Map<String,Method> map = new HashMap<>();
        while (searchClass != null && searchClass != Object.class){
            for (Method method : Arrays.asList(searchClass.getMethods())) {
                if (method.getName().startsWith(METHOD_GETTER) && BaseUtil.objectNotNull(method.invoke(t))){
                    map.put(method.getName(),method);
                }
            }
            searchClass = searchClass.getSuperclass();
        }
        return map;
    }

    /**
     * 获取类的方法,通过方法名前缀（非私有方法）
     * @param t
     * @param prefix
     * @return
     */
    public static List<Method> getMethodsByPrefix(Class t,String prefix){
        Method[] methods = t.getMethods();
        List<Method> methodList = Arrays.asList(methods);
        List<Method> prefixMethodList = methodList.stream().filter(e -> e.getName().startsWith(prefix)).collect(Collectors.toList());
        return prefixMethodList;
    }

    /**
     * 替换字符串中的指定位置
     * @param oldStr
     * @param rStr
     * @param position
     * @return
     */
    public static String replaceString(String oldStr, String rStr, int position) {
        StringBuilder sb = new StringBuilder();
        if(position == 0){
            sb.append(rStr)
              .append(oldStr.substring(position+1,oldStr.length()));
        }else if(position == oldStr.length()){
            sb.append(oldStr.substring(position-1))
              .append(rStr);
        }else {
            sb.append(oldStr.substring(0,position))
              .append(rStr)
              .append(oldStr.substring(position+1,oldStr.length()));
        }
        return sb.toString();
    }

    /**
     * 通过前缀筛选,获取domainClass中的方法
     * @param domainClass
     * @param prefix
     * @return  map集合以方法名为key
     */
    public static Map<String, Method> convertMethodsToMap(Class domainClass,String prefix){
        List<Method> methodsByPrefix = getMethodsByPrefix(domainClass, prefix);
        Method[] methods = methodsByPrefix.toArray(new Method[methodsByPrefix.size()]);
        List<Method> methodList = Arrays.asList(methods);
        Map<String, Method> collect = methodList.parallelStream()
                .collect(Collectors.toMap(
                        Method::getName, domain -> domain, (k1, k2) -> k2)
                );
        return collect;
    }
}








