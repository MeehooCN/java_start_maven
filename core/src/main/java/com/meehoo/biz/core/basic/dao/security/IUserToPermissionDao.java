package com.meehoo.biz.core.basic.dao.security;

import com.meehoo.biz.core.basic.domain.security.UserToPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

/**
 * @author zc
 * @date 2020-04-20
 */
public interface IUserToPermissionDao extends JpaRepository<UserToPermission,String>{
    @Modifying
    void deleteByUser_Id(String userId);

    List<UserToPermission> getByUserId(String userId);
}
