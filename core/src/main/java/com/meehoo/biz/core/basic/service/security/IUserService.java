package com.meehoo.biz.core.basic.service.security;


import com.meehoo.biz.core.basic.domain.security.Role;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.bos.PageRO;
import com.meehoo.biz.core.basic.ro.bos.SearchConditionListRO;
import com.meehoo.biz.core.basic.ro.security.OrganizationRoleRO;
import com.meehoo.biz.core.basic.ro.security.UserRO;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.security.OrganizationWithUserVO;
import com.meehoo.biz.core.basic.vo.security.RoleVO;
import com.meehoo.biz.core.basic.vo.security.UserOrganizationVO;
import com.meehoo.biz.core.basic.vo.security.UserVO;

import java.util.List;
import java.util.Map;

/**
 * Created by CZ on 2017/10/19.
 */
public interface IUserService extends IBaseService {
    User getUserByName(String userName) throws Exception;

    User getUserByNumber(String userNumber) throws Exception;

    void saveOrganizationRoleLink(OrganizationRoleRO organizationRoleRO) throws Exception;

    UserVO getUserOrgList(String userId) throws Exception;

    List<UserOrganizationVO> getUserOrgAndRoleList(String userId) throws Exception;

    void saveOrganizationLink(User user, List<String> orgIdList, boolean isForceMerge) throws Exception;

    List<RoleVO> getUserHasRoleList(String userId, String organizationId) throws Exception;

    List<Role> getRolesByUserAndOrg(String userId, String organizationId) throws Exception;

    String createSaveUser(UserRO userPO) throws Exception;

    String updateSaveUser(UserRO userPO) throws Exception;

    UserVO getById(String id) throws Exception;

    PageResult<UserVO> listPage(List<SearchCondition> searchConditionList,PageRO pageRO) throws Exception;

    List<UserVO> listAll(SearchConditionListRO searchConditionListRO) throws Exception;

    void createKrpanoUser(String phone, String username, String name, String id) throws Exception;

    void updateKrpanoUser(String phone, String name, String id) throws Exception;

    Map<String,List<OrganizationWithUserVO>> classifyByOrganization() throws Exception;
}
