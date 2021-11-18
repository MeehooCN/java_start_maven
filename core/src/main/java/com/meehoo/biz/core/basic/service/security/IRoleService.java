package com.meehoo.biz.core.basic.service.security;



import com.meehoo.biz.core.basic.domain.security.Role;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.security.RoleVO;

import java.util.List;

/**
 * Created by CZ on 2017/10/19.
 */
public interface IRoleService extends IBaseService {
    RoleVO getRoleByNumber(String number) throws Exception;

    Role queryByNumber(String number) throws Exception;

    List<Role> listAll() throws Exception;

    List<Role> getRolesByUserAndOrg(String userId, String organizationId) throws Exception;

    List<RoleVO> listByRoleType(Integer roleType) throws Exception;

    String getNextCode();
}
