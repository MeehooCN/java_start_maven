package com.meehoo.biz.common.util;

import com.meehoo.biz.common.util.BigDecimalUtil;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

import static java.util.stream.Collectors.*;

/**
 * Created by CZ on 2017/12/18.
 */
public class StreamUtil {

    private static boolean collectionNotEmpty(Collection collection) {
        if (null != collection && collection.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 将集合对象根据泛型对象中的某个字段的断言过滤出为true的集合
     *
     * @param collection
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> Collection<T> filter(Collection<T> collection, Predicate<? super T> predicate) {
        if (collectionNotEmpty(collection)) {
            if (collection instanceof List) {
                return collection.parallelStream().filter(predicate).collect(toList());
            }
            if (collection instanceof Set) {
                return collection.parallelStream().filter(predicate).collect(toSet());
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * 从集合对象中抽取泛型对象的某个字段，保证顺序但不会去重
     *
     * @param collection 被抽取集合对象
     * @param function   抽取函数，一般为lambda表达式或者双冒号表达式
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> extractFieldToList(Collection<T> collection, Function<T, R> function) {
        if (collectionNotEmpty(collection)) {
            return collection.parallelStream().map(function).collect(toList());
        }
        return new ArrayList<>(0);
    }

    /**
     * 从集合对象中抽取泛型对象的某个字段，不保证顺序但会去重
     *
     * @param collection 被抽取集合对象
     * @param function   抽取函数，一般为lambda表达式或者双冒号表达式
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Set<R> extractFieldToSet(Collection<T> collection, Function<T, R> function) {
        if (collectionNotEmpty(collection)) {
            return collection.parallelStream().map(function).collect(toSet());
        }
        return new HashSet<>(0);
    }

    /**
     * 将集合对象转为Map对象，key为集合中泛型对象的某个字段，当Map中新添加的key与已存在的key重复时，会保留原来key对应的value
     *
     * @param collection 被抽取集合对象
     * @param function   抽取函数，一般为lambda表达式或者双冒号表达式
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Map<R, T> convertCollectionToMap(Collection<T> collection, Function<T, R> function) {

        if (collectionNotEmpty(collection)) {
            return collection.parallelStream().collect(toMap(function, value -> value, (oldKey, newKey) -> oldKey));
        }
        return new HashMap<>(0);
    }

    public static <T, R, U> Map<R, U> convertCollectionToMap(Collection<T> collection, Function<T, R> keyFunction, Function<T, U> valueFunction) {
        if (collectionNotEmpty(collection)) {
            return collection.parallelStream().collect(toMap(keyFunction, valueFunction, (oldKey, newKey) -> oldKey));
        }
        return Collections.emptyMap();
    }

    /**
     * 将集合对象按泛型对象中的某个字段分组,保证顺序但组内不去重
     *
     * @param collection
     * @param classifier
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> Map<K, List<T>> groupCollectionInList(Collection<T> collection, Function<T, K> classifier) {

        if (collectionNotEmpty(collection)) {
            return collection.parallelStream().collect(groupingBy(classifier));
        }

        return new HashMap<>(0);
    }

    /**
     * 将集合对象按泛型对象中的某个字段分组,不保证顺序但组内会去重
     *
     * @param collection
     * @param classifier
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> Map<K, Set<T>> groupCollectionInSet(Collection<T> collection, Function<T, K> classifier) {
        if (collectionNotEmpty(collection)) {
            return collection.parallelStream().collect(groupingBy(classifier, toSet()));
        }
        return new HashMap<>(0);
    }

    /**
     * 将集合对象按泛型对象中的某个字段的断言分成true、false两组，保证顺序但组内不会去重
     *
     * @param collection
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> Map<Boolean, List<T>> partCollectionInList(Collection<T> collection, Predicate<? super T> predicate) {
        if (collectionNotEmpty(collection)) {
            return collection.parallelStream().collect(partitioningBy(predicate));
        }
        return new HashMap<>(0);
    }

    /**
     * 将集合对象按泛型对象中的某个字段的断言分成true、false两组，不保证顺序但组内会去重
     *
     * @param collection
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> Map<Boolean, Set<T>> partCollectionInSet(Collection<T> collection, Predicate<? super T> predicate) {
        if (collectionNotEmpty(collection)) {
            return collection.parallelStream().collect(partitioningBy(predicate, toSet()));
        }
        return new HashMap<>(0);
    }
    /**
     * 对集合对象按泛型对象中的某个double类型的字段进行求和
     * @param collection
     * @param toDoubleFunction
     * @param <T>
     * @return
     */
    public static <T> double sumToDouble(Collection<T> collection, ToDoubleFunction<T> toDoubleFunction) {
        if (collectionNotEmpty(collection)) {
            return collection.stream().mapToDouble(toDoubleFunction).reduce(0D, BigDecimalUtil::add);
        }
        return 0D;
    }
}
