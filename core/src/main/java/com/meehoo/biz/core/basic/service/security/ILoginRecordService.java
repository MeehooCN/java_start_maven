package com.meehoo.biz.core.basic.service.security;


import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.service.IBaseService;

/**
 * @author zc
 * @date 2019-08-01
 */
public interface ILoginRecordService extends IBaseService {

    String login(String userName, String pwd) throws Exception;

    boolean isExpired(String token) throws Exception;

    void exit(String token) throws Exception;

    User login1(String userName, String pwd) throws Exception;
}
