package com.meehoo.biz.core.basic.dao.security;


import com.meehoo.biz.core.basic.domain.security.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IAdminDao extends JpaRepository<Admin, String> {

	/**
	 * 登录时查询信息
	 * 通过username登录
	 * @param userName
	 * @return
	 */
	@Query("FROM Admin t WHERE t.userName = ?1 ")
	List<Admin> queryByUserName(String userName);

}
