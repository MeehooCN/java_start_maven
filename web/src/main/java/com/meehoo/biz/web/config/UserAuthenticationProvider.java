package com.meehoo.biz.web.config;

import org.springframework.stereotype.Component;

//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Component
public class UserAuthenticationProvider
//        implements AuthenticationProvider
{
//    private final IAdminService adminService;
//    private final IUserService userService;
//
//    public UserAuthenticationProvider(IAdminService adminService,
//                                      IUserService userService) {
//        this.adminService = adminService;
//        this.userService = userService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication)
//            throws AuthenticationException {
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//        String name = token.getName();
//        String password = authentication.getCredentials().toString();
//
//        Object object = null;
//        boolean passed = false;
//        try {
//            object = adminService.queryByUserName(name);
//            if (object!=null){
//                passed = password.equals(((Admin) object).getPassword());
//            }
////            else{
////                object = userService.getUserByName(name);
////                if (object !=null){
////                    passed = password.equals(((User) object).getPassword());
////                }
////            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (object == null||!passed) {
//            throw new UsernameNotFoundException("原因：用户名或密码错误");
//        }
//        // 授权
//        Collection<? extends GrantedAuthority> authorities = createAuthorities(object);
//        return new UsernamePasswordAuthenticationToken(object, password,
//                authorities);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.equals(authentication);
//    }
//
//    private List<GrantedAuthority> ROLE_ADMIN,ROLE_USER;
//
//    private Collection<? extends GrantedAuthority> createAuthorities(Object object){
//        if(object instanceof Admin){
//            if (ROLE_ADMIN == null){
//                ROLE_ADMIN = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
//            }
//            return ROLE_ADMIN;
//        }else  if(object instanceof User){
//            if (ROLE_USER == null){
//                ROLE_USER = AuthorityUtils.createAuthorityList("ROLE_USER");
//            }
//            return ROLE_USER;
//        }
//        throw new RuntimeException("登录类型不符");
//    }

}
