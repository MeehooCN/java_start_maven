package com.meehoo.biz.core.basic.util;


import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.annotation.Operator;
import com.meehoo.biz.core.basic.annotation.SerialNumber;
import com.meehoo.biz.core.basic.annotation.SetBySystem;
import com.meehoo.biz.core.basic.enumeration.CommonMethod;
import com.meehoo.biz.core.basic.enumeration.FieldType;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.Time;
import java.util.*;

/**
 * 反射工具
 * Created by CZ on 2018/1/4.
 */
public class ReflectUtil {

    private static final String PREFIX_GETTER = "get";

    private static final String PREFIX_SETTER = "set";

    public static final String SUFFIX_RO = "RO";

    public static final String SUFFIX_ID = "Id";

    private static final Method[] NO_METHODS = {};

    private static final Field[] NO_FIELDS = {};

    private static final Map<Class<?>, Method[]> publicMethodsCache =
            new ConcurrentReferenceHashMap<Class<?>, Method[]>(256);

    private static final Map<Class<?>, Field[]> declaredFieldsCache =
            new ConcurrentReferenceHashMap<Class<?>, Field[]>(256);

    private static final Map<Class<?>, Map<FieldType, List<Field>>> fieldTypeMapCache = new ConcurrentReferenceHashMap<>(256);

    /**
     * 清空字段、方法、域模型字段类型的缓存
     */
    public static void clearCache() {
        publicMethodsCache.clear();
        declaredFieldsCache.clear();
        fieldTypeMapCache.clear();
    }

    /**
     * 根据字段名查找字段对象(无视修饰符)
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field findField(Class<?> clazz, String fieldName) {
        if (clazz != null && StringUtil.stringNotNull(fieldName)) {
            Class<?> searchType = clazz;
            while (Object.class != searchType && searchType != null) {
                Field[] fields = getDeclaredFields(searchType);
                for (Field field : fields) {
                    if (fieldName.equals(field.getName()))
                        return field;
                }
                searchType = searchType.getSuperclass();
            }
        }
        return null;
    }

    /**
     * 获取一个类中所有的字段(包括继承)
     *
     * @param clazz
     * @return
     */
    public static Field[] getAllFields(Class<?> clazz) {
        Field[] result = NO_FIELDS;
        if (BaseUtil.objectNotNull(clazz)) {
            Class<?> searchType = clazz;
            while (Object.class != searchType && searchType != null) {
                Field[] temp = getDeclaredFields(searchType);
                result = BaseUtil.arrayConcat(result, temp);
                searchType = searchType.getSuperclass();
            }
        }
        return result;
    }

    /**
     * 获取字段的值
     *
     * @param field
     * @param target
     * @return
     */
    public static Object getFieldValue(Field field, Object target) {
        if (!(field == null || target == null)) {
            if (!field.isAccessible())
                field.setAccessible(true);
            try {
                return field.get(target);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 设置字段的值
     *
     * @param field
     * @param target
     * @param value
     */
    public static void setField(Field field, Object target, Object value) {
        if (field != null) {
            if (!Modifier.isFinal(field.getModifiers())) {
                if (!field.isAccessible())
                    field.setAccessible(true);
                try {
                    field.set(target, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Method findMethod(Class<?> clazz, String methodName) {
        return findMethod(clazz, methodName, null);
    }

    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        if (BaseUtil.objectNotNull(clazz) && StringUtil.stringNotNull(methodName)) {
            Method[] methods = getPublicMethods(clazz);
            for (Method method : methods) {
                if (methodName.equals(method.getName()) &&
                        (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
        }
        return null;
    }

    private static Method[] getPublicMethods(Class<?> clazz) {
        Method[] result = clazz.getMethods();
        if (result == null) {
            result = clazz.getMethods();
            publicMethodsCache.put(clazz, result.length == 0 ? NO_METHODS : result);
        }
        return result;
    }

    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, null);
    }

    /**
     * 执行某对象的某个方法
     *
     * @param method
     * @param target
     * @param args
     * @return
     */
    public static Object invokeMethod(Method method, Object target, Object... args) {
        if (BaseUtil.objectNotNull(method) && BaseUtil.objectNotNull(target)) {
            if (Modifier.isPublic(method.getModifiers())) {
                try {
                    return method.invoke(target, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取公共属性值
     *
     * @param clazz
     * @param target
     * @param commonMethod
     * @return
     */
    public static Object getCommonProperty(Class<?> clazz, Object target, CommonMethod commonMethod) {
        if (commonMethod == null)
            throw new IllegalArgumentException("参数CommMethod不能为空");
        return invokeMethod(findMethod(clazz, commonMethod.toString()), target);
    }

    public static void setCommProperty(Class<?> clazz, Object target, Object value, CommonMethod commonMethod) {
        if (commonMethod == null)
            throw new IllegalArgumentException("参数CommMethod不能为空");
        invokeMethod(findMethod(clazz, commonMethod.toString()), target, value);
    }

    private static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] result = declaredFieldsCache.get(clazz);
        if (result == null) {
            result = clazz.getDeclaredFields();
            declaredFieldsCache.put(clazz, (result.length == 0 ? NO_FIELDS : result));
        }
        return result;
    }

    public static String fNameToGetter(String name) {
        if (StringUtil.stringNotNull(name)) {
            StringBuilder result = new StringBuilder(PREFIX_GETTER);
            result.append(toUpperFistChar(name));
            return result.toString();
        }
        return "";
    }

    public static String fNameToSetter(String name) {
        if (StringUtil.stringNotNull(name)) {
            StringBuilder result = new StringBuilder(PREFIX_SETTER);
            result.append(toUpperFistChar(name));
            return result.toString();
        }
        return "";
    }

    /**
     * 获取泛型参数
     *
     * @param genericType
     * @return
     */
    public static Class<?>[] getActualTypes(Type genericType) {
        if (genericType != null) {
            if (genericType instanceof ParameterizedType) {
                Type[] actualTypes = ((ParameterizedType) genericType).getActualTypeArguments();
                Class[] classes = new Class[actualTypes.length];
                for (int i = 0; i < actualTypes.length; i++) {
                    classes[i] = (Class) actualTypes[i];
                }
                return classes;
            }
        }
        return null;
    }

    public static Map<FieldType, List<Field>> divideDomainFields(Class<?> dClass) {
        if (!isDomainClass(dClass))
            throw new IllegalArgumentException(dClass.getName() + "不是域模型");
        Map<FieldType, List<Field>> filedTypeMap = fieldTypeMapCache.get(dClass);
        if (filedTypeMap == null) {
            filedTypeMap = new EnumMap<>(FieldType.class);
            fieldTypeMapCache.put(dClass, filedTypeMap);
            Class<?> searchType = dClass;
            while (Object.class != searchType && searchType != null) {
                Field[] declaredFields = getDeclaredFields(searchType);
                for (Field declaredField : declaredFields) {
                    FieldType fieldType = getFiledType(declaredField);
                    if (fieldType != null) {
                        List<Field> fieldList = filedTypeMap.get(fieldType);
                        if (fieldList == null) {
                            fieldList = new ArrayList<>(0);
                            filedTypeMap.put(fieldType, fieldList);
                        }
                        fieldList.add(declaredField);
                    }
                }
                searchType = searchType.getSuperclass();
            }
        }
        return filedTypeMap;
    }

    private static boolean isDomainClass(Class<?> dClass) {
        if (dClass != null) {
            Annotation[] annotations = dClass.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                Class annotationType = annotation.annotationType();
                if (annotationType == javax.persistence.Entity.class) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private static FieldType getFiledType(Field field) {
        if (field.getAnnotation(SerialNumber.class) != null)
            return FieldType.NUMBER;
        if (field.getAnnotation(Operator.class) != null)
            return FieldType.OPERATOR;
        if (field.getAnnotation(SetBySystem.class) != null) {
            return FieldType.SETBYSYS;
        }
        if (field.getAnnotation(javax.persistence.Column.class) != null)
            return FieldType.COLUMN;
        if (field.getAnnotation(javax.persistence.ManyToOne.class) != null || field.getAnnotation(javax.persistence.OneToOne.class) != null) {
            return FieldType.FOREIGN;
        }
        if (field.getAnnotation(javax.persistence.OneToMany.class) != null || field.getAnnotation(javax.persistence.ManyToOne.class) != null)
            return FieldType.COLLECTION;
        return null;
    }

    public static Field mapDomainFieldToROField(FieldType fieldType, Field dField, Class<?> roClass) {
        if (fieldType == null) {
            throw new IllegalArgumentException("fieldType参数不能为空");
        }
        switch (fieldType) {
            case COLUMN: {
                String fieldName = dField.getName();
                return findField(roClass, fieldName);
            }
            case FOREIGN: {
                String fieldName = dField.getName() + SUFFIX_ID;
                Field foreignField = findField(roClass, fieldName);
                if (foreignField == null) {
                    fieldName = dField.getName() + SUFFIX_RO;
                    foreignField = findField(roClass, fieldName);
                }
                return foreignField;
            }
            case COLLECTION: {
                StringBuilder sb = new StringBuilder(dField.getName());
                int index = sb.lastIndexOf("List");
                if (index <= 0) {
                    throw new IllegalArgumentException(dField.getDeclaringClass().getName() + "的" + dField.getName() + "字段命名不符合规范");
                }
                sb.insert(index, "RO");
                return findField(roClass, sb.toString());
            }
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Integer s = 1;
        System.out.println(s.getClass().isPrimitive());
    }

    //将RO字段的值转为适配域模型字段类型的值
    public static Object adaptROFieldValue(Field domainField, Field roField, Object ro) throws Exception {
        Class domainFieldType = domainField.getType();
        Class roFieldType = roField.getType();
        Object value = ReflectUtil.getFieldValue(roField, ro);

        if (value != null) {
            if (isPrimitiveType(roFieldType) && isPrimitiveType(domainFieldType)) {
                return value;
            }
            if (Date.class == domainFieldType) {
                if (String.class == roFieldType) {
                    return DateUtil.stringToDate(value.toString());
                }else if (Long.class == roFieldType){
                    return new Date((Long) value);
                }else if (Date.class == roFieldType){
                    return value;
                }else{
                    throw new ClassCastException("不能解析为date的类型");
                }
            }
            if (java.sql.Date.class == domainFieldType) {
                if (String.class != roFieldType) {
                    throw new ClassCastException("非字符串类型不能转换成日期对象");
                }
                if (StringUtil.stringNotNull(value.toString())) {
                    return new java.sql.Date(DateUtil.stringToDate(value.toString()).getTime());
                }
            }
            if (Time.class == domainFieldType) {
                if (String.class != roFieldType) {
                    throw new ClassCastException("非字符串类型不能转换成时间对象");
                }
                if (StringUtil.stringNotNull(value.toString())) {
                    String timeString = value.toString();
                    return Time.valueOf(timeString);
                }
            }
        }
        return null;
    }

    private static boolean isPrimitiveType(Class<?> fieldType) {

        if (fieldType == String.class || fieldType == Integer.class ||
                fieldType == Double.class ||
                fieldType == Long.class ||
                fieldType == Float.class ||
                fieldType == Short.class ||
                fieldType == Character.class ||
                fieldType == Byte.class) {
            return true;
        }
        return fieldType.isPrimitive();
    }

    private static String toUpperFistChar(String s) {
        char[] chars = s.toCharArray();
        char firstChar = chars[0];
        if (97 <= firstChar && firstChar <= 122) {
            firstChar ^= 32;
        }
        chars[0] = firstChar;
        return String.valueOf(chars);
    }

    public static List extractFieldToList(List objectList, Field field) {
        if (BaseUtil.collectionNotNull(objectList)) {
            List results = new ArrayList();
            for (Object obj : objectList) {
                results.add(getFieldValue(field, obj));
            }
            return results;
        }
        return new ArrayList(0);
    }

    public static <D> Map<Object, D> convertDomainListToMap(Collection<D> domains, Class<D> dClass) {
        if (domains != null && domains.size() > 0) {
            Map<Object, D> result = new HashMap();
            for (D domain : domains) {
                Object id = getIdProperty(dClass, domain);
                if (BaseUtil.objectNotNull(id)) {
                    result.put(id, domain);
                }
            }
            return result;
        }
        return new HashMap<>(0);
    }

    public static Object getIdProperty(Class<?> dClass, Object domain) {
        Field idField = findField(dClass, "id");
        return getFieldValue(idField, domain);
    }

    public static Object getIdProperty(Object domain) {
        if (domain != null) {
            return getIdProperty(domain.getClass(), domain);
        }
        return null;
    }

    public static Object setIdProperty(Class<?> dClass, Object domain, Object id) {
        Field idField = findField(dClass, "id");
        setField(idField, domain, id);
        return domain;
    }

    public static void setIdProperty(Object domain, Object id) {
        if (domain != null) {
            setIdProperty(domain.getClass(), domain, id);
        }
    }

    public static <T> T setDomainId(T domain, Object id) {
        setIdProperty(domain, id);
        return domain;
    }


/*    private static Method[] getDeclaredMethods(Class<?> clazz) {
        Method[] result = publicMethodsCache.get(clazz);
        if (result == null) {
            result = clazz.getDeclaredMethods();
            publicMethodsCache.put(clazz, (result.length == 0 ? NO_METHODS : result));
        }
        return result;
    }*/

}
