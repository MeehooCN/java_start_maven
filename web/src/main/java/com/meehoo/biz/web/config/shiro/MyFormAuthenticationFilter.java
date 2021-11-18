package com.meehoo.biz.web.config.shiro;

import com.google.gson.Gson;
import com.meehoo.biz.core.basic.param.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zc
 * @date 2020-04-17
 */
@Slf4j
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 如果isAccessAllowed返回false 则执行onAccessDenied
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
//            if (request.getRemoteAddr().startsWith("192.168.3.161")){
//                return true;
//            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
//        return true;
    }

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     *
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        log.info("未登录禁止访问");
        saveRequest(request);
        setHeader((HttpServletRequest) request, (HttpServletResponse) response);

        ServletOutputStream out = response.getOutputStream();
//        PrintWriter out = response.getWriter();
        ((HttpServletResponse) response).setStatus(200);
        Map<String,Object> re = new HashMap<>();
        re.put("flag", HttpResult.Flag_NotLogin);
        re.put("msg","not login");
        String result = new Gson().toJson(re);
        out.write(result.getBytes());
        out.flush();
        out.close();
        return false;
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response) {
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(200);
    }
}
