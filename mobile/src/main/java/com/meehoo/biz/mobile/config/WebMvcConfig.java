package com.meehoo.biz.mobile.config;

import com.meehoo.biz.mobile.handler.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ResourceBundle;

/**
 * Created by wangjian on 2018-06-22.
 * mvc配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new TokenInterceptor());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("index");
        registry.addViewController("/").setViewName("index");
    }

    /**
     * 静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        ResourceBundle bundle = ResourceBundle.getBundle("uploadPath");
        String picDiskPath = bundle.getString("picDiskPath");
        registry.addResourceHandler("/static/img/**").addResourceLocations("file:"+picDiskPath);
        registry.addResourceHandler("/static/resfile/**").addResourceLocations("file:"+picDiskPath);
        super.addResourceHandlers(registry);
    }

}
