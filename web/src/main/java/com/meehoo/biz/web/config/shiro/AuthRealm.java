package com.meehoo.biz.web.config.shiro;

/**
 * @author zc
 * @date 2020-04-16
 */
import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.service.security.IAdminService;
import com.meehoo.biz.core.basic.service.security.IUserToPermissionService;
import com.meehoo.biz.core.basic.vo.security.ApiPermissionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class AuthRealm extends AuthorizingRealm {

//    @Resource
    @Autowired
    private IAdminService adminService;

    @Autowired
    private IUserToPermissionService userToPermissionService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("调用授权方法");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        authorizationInfo.addRole(user.getRoleName());
        try {
            List<ApiPermissionVO> apiPermissionVOS = userToPermissionService.getByUser(user.getId());
            for (ApiPermissionVO apiPermissionVO : apiPermissionVOS) {
                authorizationInfo.addStringPermission(apiPermissionVO.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    /**
     * 认证(主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确)
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("调用认证方法");
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        if (username == null) {
            throw new AuthenticationException("账号名为空，登录失败！");
        }

        log.info("credentials:" + token.getCredentials());
        Admin userInfo = null;
        try {
            userInfo = adminService.queryByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userInfo == null) {
            throw new AuthenticationException("不存在的账号，登录失败！");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,                                               //用户
                userInfo.getPassword(),                                 //密码
//                ByteSource.Util.bytes("md5!@#"),   //加盐后的密码
                getName()                                               //指定当前 Realm 的类名
        );
        return authenticationInfo;
    }
}
