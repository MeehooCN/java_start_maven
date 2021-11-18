package com.meehoo.biz.core.basic.util;

import com.meehoo.biz.core.basic.sql.SqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zc
 * @date 2019-04-19
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static void copyProperties(Map source, Object target){
        Class<?> targetClass = target.getClass();
        Method[] methods = targetClass.getMethods();

        Map<String,Method> name_method = new HashMap<>(methods.length/2);
        for (Method method : methods) {
            String name = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (name.startsWith("set")&&parameterTypes.length==1){
                String key = name.substring(3, name.length());
                name_method.put(key.toLowerCase(),method);
            }
        }

        for (Object key : source.keySet()) {
            String keyStr = String.valueOf(key).toLowerCase();
            if (name_method.containsKey(keyStr)){
                Method method = name_method.get(keyStr);
                try {
                    Object value = source.get(key);
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes[0]==Integer.class){
                        value = SqlUtil.objectToInt(value);
                    }
                    method.invoke(target, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void copyProperties(Object source,Object target){
        if (source instanceof Map){
            copyProperties((Map)source, target);
        }else{
            BeanUtils.copyProperties(source,target);
        }
    }
}
