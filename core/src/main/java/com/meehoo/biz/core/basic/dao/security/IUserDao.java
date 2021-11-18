package com.meehoo.biz.core.basic.dao.security;

import com.meehoo.biz.core.basic.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by CZ on 2017/10/19.
 */
public interface IUserDao extends JpaRepository<User, String> {

    @Query("FROM User u WHERE u.userName = ?1")
    List<User> queryByUserName(String username);

    @Query("FROM User u WHERE u.code = ?1")
    List<User> queryByNumber(String number);

    User getByPhone(String phone);

    User getByUserNameAndPassword(String userName, String password);
}
