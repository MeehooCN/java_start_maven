package com.meehoo.biz.core.basic.dao;

import com.meehoo.biz.common.util.BaseUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository("baseDao")
public class BaseDao implements IBaseDao {

	@PersistenceContext
	private EntityManager em;

	public Session getSession() {
		return (Session) em.getDelegate();
	}
	
	public void save(Object obj) {
		em.persist(obj);
	}

	public <T> boolean batchUpdate(List<T> objects) {
		//为了提升性能，我们对数据进行分批次的保存，手动控制更新到数据库
		boolean flag = false;
		try {
			// 每1000条往数据库里写入一次,相对提升性能，此值可改变
			int batchSize = 1000;
			int i = 0;
			for (T entity : objects) {
				em.merge(entity);
				i++;
				if (i % batchSize == 0) {
					em.flush();
					em.clear();
				}
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public <T> boolean batchSave(List<T> objects) {
		//为了提升性能，我们对数据进行分批次的保存，手动控制更新到数据库
		boolean flag = false;
		try {
			// 每100条往数据库里写入一次,相对提升性能，此值可改变
			int batchSize = 1000;
			int i = 0;
			for (T entity : objects) {
				em.persist(entity);
				i++;
				if (i % batchSize == 0) {
					em.flush();
					em.clear();
				}
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public void update(Object obj) {
		em.merge(obj);
	}

	public void delete(Object obj) {
		em.remove(obj);
	}

	public <T> T queryById(Class<T> entityClass, Object id) {
		return em.find(entityClass, id);
	}

	public <T> void deleteById(Class<T> entityClass, Object id) {
		Object obj = queryById(entityClass, id);
		delete(obj);
	}


	@SuppressWarnings("unchecked")
	public <T> List<T> queryByJPQL(String queryString, Object... values) {
		Query queryObject = em.createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			queryObject.setParameter(i + 1, values[i]);
		}
		return queryObject.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryBySQL(String sql, Object... values) {
		Query queryObject = em.createNativeQuery(sql);
		for (int i = 0; i < values.length; i++) {
			queryObject.setParameter(i+1, values[i]);
		}
		return queryObject.getResultList();
	}

	public Integer updateBySql(String sql, Object... values) {
		Query query = em.createNativeQuery(sql);
		if(BaseUtil.objectNotNull(values) && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		return query.executeUpdate();
	}
}
