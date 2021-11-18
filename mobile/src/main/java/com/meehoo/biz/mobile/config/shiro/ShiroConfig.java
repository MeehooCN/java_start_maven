package com.meehoo.biz.mobile.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.*;

/**
 * https://github.com/smltq/spring-boot-demo/blob/master/shiro/HELP.md
 * @author zc
 * @date 2020-04-16
 */
@Configuration
@Slf4j
public class ShiroConfig {

    @Autowired
    private IgnoreAuthUrlProperties ignoreAuthUrlProperties;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        log.info("Shiro过滤器开始处理");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置登录页
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后跳转页面
        shiroFilterFactoryBean.setSuccessUrl("/loginSuccess");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        //拦截器
        Map<String, String> filterMap = new LinkedHashMap<>();

        //anon:所有url都都可以匿名访问
        Set<String> urlSet = new HashSet<>(ignoreAuthUrlProperties.getIgnoreAuthUrl());
        urlSet.stream().forEach(temp -> filterMap.put(temp, "anon"));

        //用户未登录不进行跳转,返回错误信息
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new MyFormAuthenticationFilter());

        //配置退出 过滤器
        filterMap.put("/logout", "logout");

        //authc:所有url都必须认证通过才可以访问
        filterMap.put("/biz/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     *
     * @return
     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        //散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        //散列的次数，比如散列两次，相当于 md5(md5(""));
////        hashedCredentialsMatcher.setHashIterations(2);
//        return hashedCredentialsMatcher;
//    }

    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();

        CredentialsMatcher matcher = ((authenticationToken, authenticationInfo)->{
                Object credentials = authenticationToken.getCredentials();
                char[] tokenPwd = (char[]) credentials;
                StringBuilder sb = new StringBuilder();
                for (char charSequence : tokenPwd) {
                    sb.append(charSequence);
                }
//                Object principal = authenticationToken.getPrincipal();

                Object userPwd = authenticationInfo.getCredentials();
//                PrincipalCollection principals = authenticationInfo.getPrincipals();
                return sb.toString().equals(userPwd);
        });
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm());
        return securityManager;
    }

    /**
     * 启用shiro注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 异常处理
     *
     * @return
     */
    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("UnauthorizedException", "unauthorized");
        r.setExceptionMappings(mappings);

        r.setDefaultErrorView("error");
        r.setExceptionAttribute("ex");
        return r;
    }
}