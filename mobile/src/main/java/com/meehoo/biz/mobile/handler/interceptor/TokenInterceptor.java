package com.meehoo.biz.mobile.handler.interceptor;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.bos.LoginRecord;
import com.meehoo.biz.core.basic.exception.TokenInvalidException;
import com.meehoo.biz.core.basic.service.security.LoginRecordServiceImpl;
import com.meehoo.biz.core.basic.util.RedisTemplateService;
import com.meehoo.biz.core.basic.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zc
 * @date 2020-01-20
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplateService redisTemplateService;

    private String[] ignoreArray = new String[]{"login/login"};

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        List<String> ignoreList = Arrays.asList(ignoreArray);
        if (!ignoreList.contains(requestURI)){
            String token = request.getHeader("token");
            if (StringUtil.stringNotNull(token)){
                LoginRecord loginRecord = redisTemplateService.get(token, LoginRecord.class);
                Date now = new Date();
                if (now.before(loginRecord.getExpireTime())){
                    loginRecord.setExpireTime(new Date(now.getTime()+ LoginRecordServiceImpl.expireLimit));
                    UserContextUtil.storeUser(loginRecord.toUser());
                }else{
                    throw new TokenInvalidException();
                }
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContextUtil.removeUser();
    }
}
