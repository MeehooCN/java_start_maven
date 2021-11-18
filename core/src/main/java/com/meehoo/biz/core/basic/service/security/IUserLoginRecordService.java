package com.meehoo.biz.core.basic.service.security;


import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.service.IBaseService;

/**
 * Created by wangjian on 2017/7/7.
 */
public interface IUserLoginRecordService extends IBaseService {

    /**
     * admin登录
     * @param admin
     * @return
     * @throws Exception
     */
    String adminLogin(Admin admin) throws Exception;

    /**
     * 职工登录
     * @param user
     * @return
     * @throws Exception
     */
    String userLogin(User user) throws Exception;

    void updateStatus(String userId, Integer status) throws Exception;

}
