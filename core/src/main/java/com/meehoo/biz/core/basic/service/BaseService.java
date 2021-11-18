package com.meehoo.biz.core.basic.service;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.common.util.StreamUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.IBaseDao;
import com.meehoo.biz.core.basic.domain.IdEntity;
import com.meehoo.biz.core.basic.handler.SearchConditionHandler;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.security.UpdateFiledRO;
import com.meehoo.biz.core.basic.util.ReflectUtil;
import com.meehoo.biz.core.basic.util.VOUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BaseService implements IBaseService {
    @Autowired
    protected IBaseDao baseDao;
    @Autowired
    private SearchConditionHandler searchConditionHandler;

    /**
     * 增
     */
    @Override
    @Transactional
    public void save(Object object) throws Exception {
        baseDao.save(object);
    }
    @Override
    @Transactional
    public void saveOrUpdate(Object object) throws Exception {
        if (BaseUtil.objectNotNull(ReflectUtil.getIdProperty(object))) {
            baseDao.update(object);
        } else {
            baseDao.save(object);
        }
    }
    @Override
    @Transactional
    public <T> boolean batchSave(List<T> objects) {
        if (BaseUtil.collectionNotNull(objects)&&objects.size()>0)
            return baseDao.batchSave(objects);
        return false;
    }

    /**
     * 删
     */
    @Override
    @Transactional
    public <T> void deleteById(Class<T> entityClass, Object primaryKey){
        baseDao.deleteById(entityClass, primaryKey);
    }
    @Override
    @Transactional
    public void delete(Object object){
        baseDao.delete(object);
    }
    @Override
    @Transactional
    public <T> void batchDelete(List<T> objects){
        if (BaseUtil.collectionNotNull(objects)) {
            Class<T> clazz = (Class<T>) objects.get(0).getClass();
            Method getId = ReflectUtil.findMethod(clazz, "getId");
            Function<T, String> function = new Function<T, String>() {
                @Override
                public String apply(T t)  {
                    try {
                        return (String) getId.invoke(t);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            };
            List<String> idList = StreamUtil.extractFieldToList(objects, function);

            if (clazz.getAnnotation(javax.persistence.Entity.class) != null) {
                String className = objects.get(0).getClass().getSimpleName();
                String hql = "delete from " + className + " t where t.id in (:idList)";
                baseDao.getSession().createQuery(hql).setParameterList("idList", idList).executeUpdate();
            } else {
                throw new IllegalArgumentException(clazz.getName() + "不是domain类");
            }
        }
    }

    /**
     * 改
     */
    @Override
    @Transactional
    public void update(Object object) throws Exception {
        baseDao.update(object);
    }

    @Override
    @Transactional
    public void updateField(Class<?> c, UpdateFiledRO updateFiledRO) throws Exception {
        Object bean = queryById(c, updateFiledRO.getId());
        updateFiledRO.getParams();
        for (Map.Entry<String,Object> entry:updateFiledRO.getParams().entrySet()){
            Field field = c.getDeclaredField(entry.getKey());
            ReflectUtil.setField(field,bean,entry.getValue());
        }
        update(bean);
    }

    @Override
    @Transactional
    public <T> boolean batchUpdate(List<T> objects) {
        return baseDao.batchUpdate(objects);
    }
    @Override
    @Transactional
    public <T> void mergeEntryList(List<T> oldEntryList, List<T> needSaveEntryList) throws Exception {

        if (BaseUtil.collectionNotNull(needSaveEntryList)) {
            batchSave((List) StreamUtil.filter(needSaveEntryList, entry -> !BaseUtil.objectNotNull(ReflectUtil.getIdProperty(entry))));
            batchUpdate(needSaveEntryList);
        }
        if (BaseUtil.collectionNotNull(oldEntryList)) {
            Class tClass = oldEntryList.get(0).getClass();
            Map<Object, T> needSaveEntryMap = ReflectUtil.convertDomainListToMap(needSaveEntryList, tClass);
            List<T> needDeleteEntryList = oldEntryList.parallelStream().filter(entry ->
                    !BaseUtil.objectNotNull(needSaveEntryMap.get(ReflectUtil.getIdProperty(tClass, entry)))
            ).collect(Collectors.toList());
            batchDelete(needDeleteEntryList);
        }

    }

    /**
     * 查
     */
    public <T> T queryById(Class<T> entityClass,@NonNull Object primaryKey){
        return baseDao.queryById(entityClass, primaryKey);
    }
    public <T> List<T> queryByIds(Class<?> clazz, Collection idList) {
        if (idList != null && idList.size() > 0) {
            String hql = "FROM " + clazz.getSimpleName() + " where id in (:idList)";
            return baseDao.getSession().createQuery(hql).setParameterList("idList", idList).list();
        }
        return new ArrayList<>();
    }
    public <T extends IdEntity> Map<String,T> queryMapByIds(Class<T> clazz, Collection idList) {
        List<T> result = queryByIds(clazz,idList);
        Map<String, T> stringTMap = StreamUtil.convertCollectionToMap(result, e -> e.getId());
        return stringTMap;
    }
    @Override
    public <T> T queryByColumn(Class<?> clazz, @NonNull String column, Object value) throws Exception {
        String hql = "FROM " + clazz.getSimpleName() + " where " + column + "=?";
        List<T> list = baseDao.queryByJPQL(hql,value);

        if (BaseUtil.collectionNotNull(list)) {
            return list.get(0);
        }

        return null;
    }
    @Override
    public <T> List<T> queryListByColumn(Class<T> clazz,@NonNull String column, Object value){
        String hql = "FROM " + clazz.getSimpleName() + " where " + column + "=?";
        return baseDao.queryByJPQL(hql,value);
    }
    /*
    ******************************************************************************** 非接口方法
     */

    @SuppressWarnings({"unchecked"})
    public <V> PageResult<V> page(DetachedCriteria dc, PageCriteria pc) throws Exception {
        dc.setProjection(Projections.rowCount());
        Long total = count(dc);
        List<V> list = null;
        if (total > 0L) {
            dc.setProjection(null);
            Criteria criteria = dc.getExecutableCriteria(baseDao.getSession());
            int first = pc.getFirstResult();
            int max = pc.getMaxResult();
            if (first > 0) {
                criteria.setFirstResult(first);
            }
            if (max > 0) {
                criteria.setMaxResults(max);
            }
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
        } else {
            list = new ArrayList<>();
        }
        return new PageResult<>(total, list);
    }

    @SuppressWarnings({"unchecked"})
    public <V> PageResult<V> page(DetachedCriteria dc, PageCriteria pc, Class<V> voClass) {
        dc.setProjection(Projections.rowCount());// 设置搜索策略:分页查询满足条件的总记录数
        Long total = count(dc);
        List<V> list = null;
        if (total > 0L) {
            dc.setProjection(null);
            Criteria criteria = dc.getExecutableCriteria(baseDao.getSession());
            int first = pc.getFirstResult();
            int max = pc.getMaxResult();
            if (first > 0) {
                criteria.setFirstResult(first);
            }
            if (max > 0) {
                criteria.setMaxResults(max);
            }
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            // 持久化po对象转化为前台VO对象
            list = VOUtil.convertDomainListToTempList(criteria.list(), voClass);
        } else {
            list = new ArrayList<>(0);
        }
        return new PageResult<>(total, list);
    }

    public Long count(DetachedCriteria dc) {
        return (Long) dc.getExecutableCriteria(baseDao.getSession()).uniqueResult();
    }

    /***
     * 通用分页查询
     * @param domainClass
     * @param voClass
     * @param pageCriteria
     * @param searchConditionList
     * @param <D> 域模型类名
     * @param <V> VO类名
     * @return
     * @throws Exception
     */
    @Override
    public <D, V> PageResult<V> listPage(Class<D> domainClass, Class<V> voClass,
                                         PageCriteria pageCriteria, List<SearchCondition> searchConditionList){
        DetachedCriteria dc = DetachedCriteria.forClass(domainClass);
        this.appendRestrictions(domainClass, dc, searchConditionList);
        return this.page(dc, pageCriteria, voClass);
    }


    @SuppressWarnings("rawtypes")
    public List list(DetachedCriteria dc) {
        Criteria criteria = dc.getExecutableCriteria(baseDao.getSession());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public <V> List<V> list(DetachedCriteria dc, Class<V> voClass) throws Exception {
        List<V> list = VOUtil.convertDomainListToTempList(this.list(dc), voClass);
        return list;
    }

    /***
     * 通用列表查询
     * @param domainClass
     * @param voClass
     * @param searchConditionList
     * @param <D> 域模型类名
     * @param <V> VO类名
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public <D, V> List<V> listAll(Class<D> domainClass, Class<V> voClass,
                                  List<SearchCondition> searchConditionList) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(domainClass);
        this.appendRestrictions(domainClass, dc, searchConditionList);
        return this.list(dc, voClass);
    }

    public <T> List<T> listAll(Class<T> clazz, List<SearchCondition> searchConditionList) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        this.appendRestrictions(clazz, dc, searchConditionList);
        return this.list(dc);
    }





    private String getClassFeildGetterReturnType(Class domainClass, String feildName, boolean retSimpleName) {
        String fNameFirstLetter = feildName.substring(0, 1).toUpperCase();
        String getMethodName = "get";
        if (feildName.length() > 1) {
            getMethodName = getMethodName + fNameFirstLetter + feildName.substring(1, feildName.length());
        } else {
            getMethodName = getMethodName + fNameFirstLetter;
        }
        Method method = null;
        try {
            method = domainClass.getMethod(getMethodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("没有对应方法");
        }
        return retSimpleName ? method.getReturnType().getSimpleName() : method.getReturnType().getName();
    }

    protected void appendRestrictions(Class domainClass, DetachedCriteria criteria, List<SearchCondition> searchConditionList) {
        for (SearchCondition sc : searchConditionList) {
            if (StringUtil.stringNotNull(sc.getName()) && StringUtil.stringNotNull(sc.getOperand())) {
                String name = sc.getName();
                String value = sc.getValue();
                String feildTypeName = "";
                if (name.contains(".")) {
                    int indexDot = name.indexOf(".");
                    String aliasName = name.substring(0, indexDot);
                    //创建别名之前需要判断别名是否存在。
                    boolean existAlias = existAlias(criteria,aliasName,aliasName);
                    if(!existAlias){//不存在就创建
                        criteria.createAlias(aliasName, aliasName);
                    }
                    String fieldName = name.substring(indexDot + 1, name.length());
                    String className = getClassFeildGetterReturnType(domainClass, aliasName, false);
                    try {
                        feildTypeName = getClassFeildGetterReturnType(Class.forName(className), fieldName, true);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        throw new RuntimeException("无对应类");
                    }
                } else {
                    feildTypeName = getClassFeildGetterReturnType(domainClass, name, true);
                }

                Object transform = null;
                transform = searchConditionHandler.transform(value, feildTypeName);
                searchConditionHandler.handle(name,transform,sc.getOperand(),criteria);
            }
        }
    }


    private boolean existAlias(Criteria c, String path, String alias) {
        Iterator itm = ((CriteriaImpl) c).iterateSubcriteria();
        while (itm.hasNext()) {
            CriteriaImpl.Subcriteria sub = (CriteriaImpl.Subcriteria) itm.next();
            if (alias.equals(sub.getAlias()) || path.equals(sub.getPath())) {
                return true;
            }
        }
        return false;
    }

    private boolean existAlias(DetachedCriteria c, String path, String alias) {
        Class clazz = c.getClass();
        try {
            Field field = clazz.getDeclaredField("criteria");
            field.setAccessible(true);
            CriteriaImpl ci;
            ci = (CriteriaImpl) field.get(c);
            return existAlias(ci, path, alias);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }
}
