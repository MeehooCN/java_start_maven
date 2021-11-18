package com.meehoo.biz.core.basic.sql;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @date 2020-08-06
 */
@Repository("SqlExecute")
public class SqlExecute {
    @PersistenceContext
    private EntityManager em;

    public List<Object[]> queryList(String sql,Object... values){
        Query query = em.createNativeQuery(sql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.getResultList();
    }

    public <T> List<T> queryList(String sql,Class<T> tClass, Object... values){
        Query query = em.createNativeQuery(sql,tClass);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.getResultList();
    }

    public List<Map<String,Object>> queryForEntry(String sql, Object... values) {
        Query query = em.createNativeQuery(sql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }
}
