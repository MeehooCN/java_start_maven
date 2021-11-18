package com.meehoo.biz.mobile.handler.aop;

import com.meehoo.biz.core.basic.exception.NotLoginException;
import com.meehoo.biz.core.basic.exception.PwdNotRightException;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.util.RedisTemplateService;
import com.meehoo.biz.core.basic.util.UserContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 访问拦截器
 * Created by CZ on 2017/10/12.
 */
@Aspect
@Component
public class AuthenticationInterceptor {
//    private ILoginRecordDao loginRecordDao;
    private RedisTemplateService redisTemplateService;


    @Autowired
    public AuthenticationInterceptor(RedisTemplateService redisTemplateService) {
//        this.loginRecordDao = loginRecordDao;
        this.redisTemplateService = redisTemplateService;
    }

//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
//            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
//            "@annotation(org.springframework.web.bind.annotation.GetMapping) ")
    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void pointCut() {

    }

    /**
     * 对切入点的方法进行环绕通知
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointCut()")
    public HttpResult aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpResult httpResult ;
        try {
//            beforeHandle(joinPoint);

            Object object = joinPoint.proceed();
            if (object instanceof HttpResult){
                httpResult = (HttpResult) object;
            }else{
                throw new RuntimeException("接口请返回HttpResult类型");
            }
//            afterHandle(object);
        }catch (Exception e){
            httpResult = handleException(e);
        }finally {
            finallyHandle();
        }
        return httpResult;
    }

    private void finallyHandle() {
        UserContextUtil.removeUser();
    }

    private void afterHandle(Object object) {

    }

    private void beforeHandle(ProceedingJoinPoint joinPoint) {
        if (joinPoint.getArgs().length>0){
            Object arg = joinPoint.getArgs()[0];
//            if (arg instanceof TokenRO){
//                String token = ((TokenRO) arg).getToken();
//                LoginRecord loginRecord = redisTemplateService.get(token,LoginRecord.class);
//                Date now = new Date();
//                if (loginRecord == null||now.after(loginRecord.getExpireTime())){
//                    throw new RuntimeException("已过期,请重新登录");
//                }
//                // 延时半个小时
//                Date expireTime = new Date(now.getTime() + 30 * 60 * 1000);
//                loginRecord.setExpireTime(expireTime);
//                redisTemplateService.set(token,loginRecord);
//                //  放进缓存中
//                User user = new User();
//                user.setId(loginRecord.getUserId());
//                user.setName(loginRecord.getUserName());
//                UserContextUtil.storeUser(user);
//            }
        }
    }

    private HttpResult handleException(Exception e){
        HttpResult httpResult = HttpResult.fail(e);
        if (e instanceof NotLoginException){
            httpResult.setFlag(HttpResult.Flag_NotLogin);
            httpResult.setMsg("未登录");
        }else if (e instanceof PwdNotRightException){
            httpResult.setFlag(HttpResult.Flag_PwdWrong);
            httpResult.setMsg("账号密码错误");
        }else if (e instanceof RuntimeException){
            httpResult.setFlag(HttpResult.Flag_Fail);
            httpResult.setMsg(e.getMessage());
        }else{
            httpResult.setFlag(HttpResult.Flag_Fail);
            httpResult.setMsg("失败");
        }
        e.printStackTrace();
        return httpResult;
    }


}
