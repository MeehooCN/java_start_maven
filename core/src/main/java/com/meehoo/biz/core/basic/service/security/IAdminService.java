package com.meehoo.biz.core.basic.service.security;


import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.UserLoginRecord;
import com.meehoo.biz.core.basic.service.IBaseService;

public interface IAdminService extends IBaseService {
	/**
	 * 通过用户名，登录时使用
	 * @param username
	 * @return
	 * @throws Exception
	 */
	Admin queryByUserName(String username) throws Exception;

	/**
	 * 用token判断当前用户是否登录查出用户信息
	 * @param token
	 */
	Admin getByToken(String token)throws Exception;

	/**
	 * 用token判断当前用户是否登录查出用户信息
	 * @param token
	 */
	UserLoginRecord getUserLoginRecordByToken(String token)throws Exception;

}
