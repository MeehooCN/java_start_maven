package com.meehoo.biz.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * tomcat部署非Root包名
 * 1.登录成功跳转路径
 * 2.菜单数据库路径
 * 3.前端菜单路径
 * @author zc
 * @date 2020-06-24
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "my")
public class ApplicationValues {
    private String context_path_web;
}
