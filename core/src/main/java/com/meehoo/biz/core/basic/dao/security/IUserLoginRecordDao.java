package com.meehoo.biz.core.basic.dao.security;

import com.meehoo.biz.core.basic.domain.security.UserLoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by qx on 2019/4/1.
 */
public interface IUserLoginRecordDao extends JpaRepository<UserLoginRecord, String> {

    @Query("FROM UserLoginRecord  WHERE userId = ?1")
    UserLoginRecord queryByUserId(String userId);

    @Modifying
    @Query("update UserLoginRecord set status = ?2 where userId = ?1")
    void updateStatus(String userId, Integer status);

}
