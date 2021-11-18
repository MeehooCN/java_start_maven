package com.meehoo.biz.core.basic.dao.security;

import com.meehoo.biz.core.basic.domain.security.UserLoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * Created by wangjian on 2017/7/6.
 */
public interface IAdminLoginRecordDao extends JpaRepository<UserLoginRecord, String> {
    /**
     * 判断是否登录以及登录是否过期
     * @param token  登录记录id
     * @param nowTime 当前时间
     * @return
     */
    @Query("FROM UserLoginRecord d WHERE d.id = ?1 AND d.availableTime >=?2 AND d.status = 1")
    UserLoginRecord findByToken(String token, Date nowTime);
}

