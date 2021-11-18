package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.core.basic.dao.security.IUserToPermissionDao;
import com.meehoo.biz.core.basic.domain.security.ApiPermission;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.domain.security.UserToPermission;
import com.meehoo.biz.core.basic.ro.security.UserToPermissionRO;
import com.meehoo.biz.core.basic.service.common.BaseServicePlus;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.security.ApiPermissionVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zc
 * @date 2020-04-20
 */
@Service
@Transactional
@AllArgsConstructor
public class UserToPermissionServiceImpl extends BaseServicePlus implements IUserToPermissionService {
    private final IUserToPermissionDao userToPermissionDao;

    @Override
    public void updatePermission(UserToPermissionRO ro) throws Exception {
        userToPermissionDao.deleteByUser_Id(ro.getUserId());

        User user = queryById(User.class, ro.getUserId());
        List<ApiPermission> apiPermissionList = queryByIds(ApiPermission.class, ro.getPermissionIds());

        List<UserToPermission> userToPermissions = apiPermissionList.stream().map(e -> {
            UserToPermission uo = new UserToPermission();
            uo.setUser(user);
            uo.setApiPermission(e);
            return uo;
        }).collect(Collectors.toList());
        batchSave(userToPermissions);
    }

    @Override
    public List<ApiPermissionVO> getByUser(String userId) throws Exception {
        List<UserToPermission> list = userToPermissionDao.getByUserId(userId);
        List<ApiPermission> apiPermissionList = list.stream().map(UserToPermission::getApiPermission).collect(Collectors.toList());
        return VOUtil.convertDomainListToTempList(apiPermissionList,ApiPermissionVO.class);
    }

    @Override
    public boolean hasPermission(String userId, String permission) throws Exception {
        List<UserToPermission> list = userToPermissionDao.getByUserId(userId);
        for (UserToPermission userToPermission : list) {
            if (permission.equals(userToPermission.getApiPermission().getName())){
                return true;
            }
        }
        return false;
    }
}
