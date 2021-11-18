package com.meehoo.biz.core.basic.service.security;


import com.meehoo.biz.core.basic.dao.security.IUserLoginRecordDao;
import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.domain.security.UserLoginRecord;
import com.meehoo.biz.core.basic.service.common.BaseServicePlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * Created by wangjian on 2017/7/7.
 */
@Service
@Transactional
public class UserLoginRecordServiceImpl extends BaseServicePlus implements IUserLoginRecordService {

    private final IUserLoginRecordDao userLoginRecordDao;
    @Autowired
    public UserLoginRecordServiceImpl(IUserLoginRecordDao userLoginRecordDao){
        this.userLoginRecordDao = userLoginRecordDao;
    }


    /**
     * 职工登录
     * @param admin
     * @throws Exception
     */
    @Override
    public String adminLogin(Admin admin) throws Exception {
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(admin.getId());
        userLoginRecord.setStatus(userLoginRecord.LOGIN);
        Calendar calendar = Calendar.getInstance();
        userLoginRecord.setLoginTime(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        userLoginRecord.setAvailableTime(calendar.getTime()); // 可用时间，登录时间+一天
        this.save(userLoginRecord);
        return userLoginRecord.getId();
    }

    @Override
    public String userLogin(User user) throws Exception {
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(user.getId());
        userLoginRecord.setStatus(userLoginRecord.LOGIN);
        Calendar calendar = Calendar.getInstance();
        userLoginRecord.setLoginTime(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        userLoginRecord.setAvailableTime(calendar.getTime()); // 可用时间，登录时间+一天
        this.save(userLoginRecord);
        return userLoginRecord.getId();
    }


    @Override
    public void updateStatus(String userId,Integer status) throws Exception {
        userLoginRecordDao.updateStatus(userId,status);
    }
}
