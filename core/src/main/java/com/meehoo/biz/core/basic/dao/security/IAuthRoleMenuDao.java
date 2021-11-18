package com.meehoo.biz.core.basic.dao.security;

import com.meehoo.biz.core.basic.domain.security.AuthRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by CZ on 2017/10/24.
 */
public interface IAuthRoleMenuDao extends JpaRepository<AuthRoleMenu, String> {


    @Query("FROM AuthRoleMenu WHERE role.id = ?1")
    List<AuthRoleMenu> queryByRoleId(String roleId);


    @Query("FROM AuthRoleMenu WHERE role.id in ?1")
    List<AuthRoleMenu> queryByRoleIdIn(List<String> roleIdList);

    @Modifying
    @Query("delete from AuthRoleMenu rm where rm.role.id = ?1")
    void deleteByRoleId(String roleId);
}
