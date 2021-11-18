package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.core.basic.ro.security.UserToPermissionRO;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.security.ApiPermissionVO;

import java.util.List;

/**
 * @author zc
 * @date 2020-04-20
 */
public interface IUserToPermissionService extends IBaseService {
    void updatePermission(UserToPermissionRO ro)throws Exception;

    List<ApiPermissionVO> getByUser(String userId)throws Exception;

    boolean hasPermission(String userId, String permission)throws Exception;
}
