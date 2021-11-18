package com.meehoo.biz.core.basic.dao;


import org.hibernate.Session;

import java.util.List;

public interface IBaseDao {
	Session getSession();
	// 增
	void save(Object object);
	<T> boolean batchSave(List<T> objects);

	// 删
	void delete(Object obj);
	<T> void deleteById(Class<T> entityClass, Object id);

	// 改
	void update(Object object);
	<T> boolean batchUpdate(List<T> objects);
	Integer updateBySql(String sql, Object... values);

	// 查
	<T> T queryById(Class<T> entityClass, Object id);
	<T> List<T> queryByJPQL(String queryString, Object... values);
	<T> List<T> queryBySQL(String sql, Object... values);










}