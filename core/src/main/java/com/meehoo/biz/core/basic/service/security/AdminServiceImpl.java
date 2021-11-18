package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.dao.security.IAdminDao;
import com.meehoo.biz.core.basic.dao.security.IAdminLoginRecordDao;
import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.UserLoginRecord;
import com.meehoo.biz.core.basic.service.common.BaseServicePlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class AdminServiceImpl extends BaseServicePlus implements IAdminService {

	private final IAdminDao dao;
	private final IAdminLoginRecordDao loginRecordDao;

	@Autowired
	public AdminServiceImpl(IAdminDao adminDao,
							IAdminLoginRecordDao loginRecordDao) {
		this.dao = adminDao;
		this.loginRecordDao = loginRecordDao;
	}

	@Override
	public Admin getByToken(String token)throws Exception{
		UserLoginRecord userLoginRecord = loginRecordDao.findByToken(token, new Date());
		if (BaseUtil.objectNotNull(userLoginRecord)) {
			Admin admin = (dao.findById(userLoginRecord.getUserId())).get();
			return admin;
		}
		return null;
	}

	@Override
	public UserLoginRecord getUserLoginRecordByToken(String token) throws Exception {
		return loginRecordDao.findByToken(token, new Date());
	}

	public Admin queryByUserName(String username) throws Exception {
		List<Admin> adminList = dao.queryByUserName(username);

		if(BaseUtil.collectionNotNull(adminList)){
			return adminList.get(0);
		}else{
			return null;
		}
	}
}
