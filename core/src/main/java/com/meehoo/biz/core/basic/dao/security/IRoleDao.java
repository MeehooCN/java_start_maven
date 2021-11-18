package com.meehoo.biz.core.basic.dao.security;

import com.meehoo.biz.core.basic.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by CZ on 2017/10/19.
 */
public interface IRoleDao extends JpaRepository<Role, String> {

    @Query("FROM Role WHERE code = ?1")
    List<Role> queryByNumber(String number);

    @Query("FROM Role WHERE name = ?1")
    List<Role> queryByName(String name);

    @Query("FROM Role WHERE roleType = ?1")
    List<Role> queryByRoleType(Integer roleType);
}
