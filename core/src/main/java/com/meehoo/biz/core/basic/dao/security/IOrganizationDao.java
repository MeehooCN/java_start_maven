package com.meehoo.biz.core.basic.dao.security;

import com.meehoo.biz.core.basic.domain.security.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by CZ on 2018/1/16.
 */
public interface IOrganizationDao extends JpaRepository<Organization, String> {
    @Query("FROM Organization WHERE parentOrg.id = ?1")
    List<Organization> findByParentOrgId(String id);

    @Query("FROM Organization WHERE grade = 0 and isLeaf ="+Organization.ISLeaf_NO)
    List<Organization> findTopOrg();

    @Query(nativeQuery=true, value = "select * from sec_org WHERE id=?1  ")
    Organization queryById(String Id);

    @Query(nativeQuery=true, value = "select * from sec_org WHERE id in ?1 order by orgType desc")
    List<Organization> queryByIdList(List<String> orgIdList);

    @Query("FROM Organization WHERE parentOrg.id = null")
    List<Organization> listRoot();

    List<Organization> findByIsDelete(int isDelete);
}