package com.meehoo.biz.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ResourceBundle;

/**
 * Created by wangjian on 2018-06-22.
 * mvc配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login-admin");
        registry.addViewController("/").setViewName("login-admin");
        registry.addViewController("/logout").setViewName("login-admin");
        registry.addViewController("/sysmanage").setViewName("sysmanage/index");
        registry.addViewController("/sysmanage/**").setViewName("sysmanage/index");
    }


    /**
     * 静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        ResourceBundle bundle = ResourceBundle.getBundle("uploadPath");
        String picDiskPath = bundle.getString("picDiskPath");
        registry.addResourceHandler("/static/resfile/**").addResourceLocations("file:"+picDiskPath);
//        registry.addResourceHandler("/**").addResourceLocations("file:"+picDiskPath);
        registry.addResourceHandler("/template/static/resfile/**").addResourceLocations("file:"+picDiskPath);
        registry.addResourceHandler("/static/img/**").addResourceLocations("file:"+picDiskPath);
        registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(2592000);
        registry.addResourceHandler("/standard/**").addResourceLocations("/standard/").setCachePeriod(2592000);
    }
}
