package com.meehoo.biz.core.basic.util;

import com.meehoo.biz.core.basic.param.PageResult;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author wanghan 2012-8-3 下午7:57:22
 */
public class VOUtil {

    /**
     * 持久化po对象转化为前台VO对象
     */

    public static <T, D, C> List<T> convertPOListToVOList(List<D> poList,
                                                          Class<C> poClass, Class<T> voClass) throws Exception {

        int size = poList.size();
        List<T> voList = new ArrayList<T>(size);

        //获取VO对象的构造函数
        Constructor<T> voconstructor = voClass.getConstructor(new Class[]{poClass});

        for (int i = 0; i < size; i++) {
            voList.add(voconstructor.newInstance(new Object[]{poList.get(i)}));
        }
        return voList;
    }

    public static <T> HashMap<String, Object> constructorJsonMap(Long total,
                                                                 List<T> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("rows", list);
        return map;

    }

    @SuppressWarnings("unchecked")
    public static <V, D> List<V> convertDomainListToTempList(List<D> domainList,
                                                             Class<V> voClass) {
        if (CollectionUtils.isEmpty(domainList)) {
            return new ArrayList<V>(0);
        } else {
            int size = domainList.size();
            List<V> tempList = new ArrayList<V>(size);
            Class<?> dClass = domainList.get(0).getClass();
            if (dClass == voClass) {
                return (List<V>) domainList;
            } else {

                // 获取temp对象的构造函数
                Constructor<V> voconstructor = null;
                try {
                    voconstructor = voClass
                            .getConstructor(new Class[]{dClass});
                    for (int i = 0; i < size; i++) {
                        tempList.add(voconstructor
                                .newInstance(new Object[]{domainList.get(i)}));
                    }
                    return tempList;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

    /**
     * 带过滤功能的convertDomainListToTempList
     * @param domainList
     * @param voClass
     * @param predicate 关于某个字段的过滤条件(为true时表示该对象会被转换)
     * @param <V>
     * @param <D>
     * @return
     * @throws Exception
     */
    public static <V, D> List<V> convertDomainListToTempListWithFilter(List<D> domainList, Class<V> voClass, Predicate<? super D> predicate) throws Exception {
        if (CollectionUtils.isEmpty(domainList)) {
            return new ArrayList<V>(0);
        } else {
            int size = domainList.size();
            List<V> tempList = new ArrayList<V>(size);
            Class<?> dClass = domainList.get(0).getClass();
            if (dClass == voClass) {
                return (List<V>) domainList;
            } else {

                // 获取temp对象的构造函数
                Constructor<V> voconstructor = voClass
                        .getConstructor(new Class[]{dClass});

                for (int i = 0; i < size; i++) {
                    D currDomain = domainList.get(i);
                    if (predicate.test(domainList.get(i))) {
                        tempList.add(voconstructor
                                .newInstance(new Object[]{currDomain}));
                    }
                }
                return tempList;
            }
        }
    }
    /**
     * 将domainPageResult转化为voPageResult
     * @param domainPageResult 需要转化的域模型分页类
     * @param voClass 被转化成的vo类型
     * @param <V>
     * @param <D>
     * @return
     * @throws Exception
     * added by wrc
     */
    public static <V, D> PageResult<V> convertToVOPageResult(PageResult<D> domainPageResult,
                                                             Class<V> voClass)throws Exception{
        Long total = domainPageResult.getTotal();
        List<D> domainList = domainPageResult.getRows();
        List<V> voList = VOUtil.convertDomainListToTempList(domainList, voClass);
        PageResult<V> voPageResult = new PageResult<>(total, voList);
        return voPageResult;
    }


}
