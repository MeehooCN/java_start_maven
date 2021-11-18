package com.meehoo.biz.web.config;

//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by wangjian on 2018-06-22.
 * spring security配置类
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig
//        extends WebSecurityConfigurerAdapter
{

//    private final AuthenticationProvider authenticationProvider;
//
//    public WebSecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }
//
//    //重写登录
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //super.configure(http);
//        http.csrf().disable();
//        http.formLogin()
//             .loginPage("/login")   //登录页面地址
//             .defaultSuccessUrl("/loginSuccess")    //登录成功跳转页面
//             .failureUrl("/login?error")   //登录失败跳转页面
//             .permitAll()
//             .and()
//             .logout()
//             .logoutUrl("/logout")    //注销路径
//             .logoutSuccessUrl("/login")   //注销成功跳转页面
//             .permitAll();
//        //权限控制  在此处设置拦截路径及权限   hasRole默认添加了"ROLE_" 前缀
//        http.authorizeRequests()
//                .antMatchers("/resources/**").permitAll()
//                .antMatchers("/static/**").permitAll()
//                .antMatchers("/loadPhoto.html").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN");
//    }
//
//    //重写获取用户信息方法
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(this.authenticationProvider);
//        //  auth.userDetailsService(loginUserDetailService).passwordEncoder(new MessageDigestPasswordEncoder("MD5"));
//    }

}
