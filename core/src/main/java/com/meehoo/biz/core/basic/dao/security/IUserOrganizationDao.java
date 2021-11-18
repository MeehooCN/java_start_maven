package com.meehoo.biz.core.basic.dao.security;

import com.meehoo.biz.core.basic.domain.security.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by CZ on 2018/1/16.
 */
public interface IUserOrganizationDao
        extends JpaRepository<UserOrganization, String>
{

    @Query("FROM UserOrganization WHERE user.id = ?1")
    List<UserOrganization> findByUserId(String userId);

    @Query("FROM UserOrganization WHERE user.id in ?1")
    List<UserOrganization> findByUserIdIn(List<String> idList);

    @Query("FROM UserOrganization WHERE user.id = ?1 and organization.id = ?2")
    UserOrganization findByUserIdAndOrganizationId(String userId, String organizationId);
}