package com.meehoo.biz.mobile.handler.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 移动端token接受和处理
 * @author zc
 * @date 2020-01-20
 */
//@Order(1)
//@WebFilter(filterName = "tokenFilter",urlPatterns = "/*")
public class TokenFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 可以从@WebFliter获取参数
        String filterName = filterConfig.getFilterName();
        System.out.println("过滤器"+filterName+"初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest){
            String token = ((HttpServletRequest) servletRequest).getHeader("token");
            System.out.println("token"+token);
        }
    }

    @Override
    public void destroy() {

    }
}
