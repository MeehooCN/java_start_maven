package com.meehoo.biz.core.basic.service;



import com.meehoo.biz.core.basic.domain.IdEntity;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.security.UpdateFiledRO;

import java.util.Collection;
import java.util.List;

public interface IBaseService {
    // 增
    void save(Object object) throws Exception;
    void saveOrUpdate(Object object) throws Exception;
    <T> boolean batchSave(List<T> objects);
    /**
     * 通用增改方法，已在BaseServicePlus中实现
     */
    default <D, RO> D createOrUpdate(Class<D> dClass, RO ro) throws Exception {
        return null;
    }

    // 删
    <T> void deleteById(Class<T> entityClass, Object primaryKey);
    void delete(Object object) throws Exception;
    <T> void batchDelete(List<T> objects) throws Exception;

    // 改
    void update(Object object) throws Exception;
    void updateField(Class<?> c,UpdateFiledRO updateFiledRO) throws Exception;
    <T> boolean batchUpdate(List<T> objects);
    <T> void mergeEntryList(List<T> oldEntryList, List<T> needSaveEntryList) throws Exception;

    // 查
    <T> T queryById(Class<T> entityClass, Object id);
    <T> List<T> queryByIds(Class<?> clazz, Collection idList) ;
    <T> T queryByColumn(Class<?> clazz, String column, Object value) throws Exception;
    <T> List<T> queryListByColumn(Class<T> clazz, String column, Object value);
    /***
     * 通用分页查询
     */
    <D, V> PageResult<V> listPage(Class<D> domainClass, Class<V> voClass, PageCriteria pageCriteria, List<SearchCondition> searchConditionList);
    /***
     * 通用列表查询
     */
    <D, V> List<V> listAll(Class<D> domainClass, Class<V> voClass, List<SearchCondition> searchConditionList) throws Exception;
}
