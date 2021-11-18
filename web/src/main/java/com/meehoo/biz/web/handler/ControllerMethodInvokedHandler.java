package com.meehoo.biz.web.handler;

import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.core.basic.annotation.HasPermission;
import com.meehoo.biz.core.basic.annotation.OutputApi;
import com.meehoo.biz.core.basic.annotation.UnAop;
import com.meehoo.biz.core.basic.domain.security.UserLoginRecord;
import com.meehoo.biz.core.basic.exception.NoPermissionException;
import com.meehoo.biz.core.basic.exception.NotLoginException;
import com.meehoo.biz.core.basic.exception.TokenInvalidException;
import com.meehoo.biz.core.basic.handler.UserManager;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.service.security.ILoginRecordService;
import com.meehoo.biz.core.basic.service.security.IUserToPermissionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 统一异常返回消息处理
 * Created by CZ on 2017/10/12.
 */
@Aspect
@Component
public class ControllerMethodInvokedHandler {
    private final Logger logger = LoggerFactory.getLogger("market");

    private final ILoginRecordService loginRecordService;
    private final IUserToPermissionService userToPermissionService;

    public ControllerMethodInvokedHandler(ILoginRecordService loginRecordService, IUserToPermissionService userToPermissionService) {
        this.loginRecordService = loginRecordService;
        this.userToPermissionService = userToPermissionService;
    }


        @Pointcut(
//                "@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) ")
//    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void pointCut() {
    }


    /**
     * 对切入点的方法进行环绕通知
     *
     * @param joinPoint
     * @return
     */
    @Around("pointCut()")
    public HttpResult aroundAdvice(ProceedingJoinPoint joinPoint){
        HttpResult httpResult ;
        try {
            beforeHandle(joinPoint);

            Object object =  joinPoint.proceed();
            if (object instanceof HttpResult){
                httpResult = (HttpResult) object;
            }else{
                throw new RuntimeException("接口请返回HttpResult类型");
            }

            afterHandle(httpResult,joinPoint);
        } catch (Throwable e) {
            httpResult = handleException(e);
        }
        return httpResult;
    }

    private void beforeHandle(ProceedingJoinPoint joinPoint)throws Exception {
        if (!getAnnotation(joinPoint, UnAop.class)&& UserManager.getCurrent()==null){
            throw new NotLoginException();
        }
        if (getAnnotation(joinPoint, OutputApi.class)){
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes)ra;
            HttpServletRequest request = sra.getRequest();
            String token = request.getHeader("token");

            if (loginRecordService.isExpired(token)){
                throw new TokenInvalidException();
            }
        }
        else if (getAnnotation(joinPoint, HasPermission.class)){
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes)ra;
            HttpServletRequest request = sra.getRequest();
            String token = request.getHeader("token");

            if (loginRecordService.isExpired(token)){
                throw new TokenInvalidException();
            }

            UserLoginRecord loginRecord = loginRecordService.queryById(UserLoginRecord.class,token);
            if (!userToPermissionService.hasPermission(loginRecord.getUserId(),"majorEvaluate")){
                throw new NoPermissionException();
            }

        }
    }

    private HttpResult handleException(Throwable e){
        HttpResult httpResult = HttpResult.fail(e);
        if (e instanceof NotLoginException){
            httpResult.setFlag(HttpResult.Flag_NotLogin);
            httpResult.setMsg("未登录");
        }
        else if (e instanceof RuntimeException){
            httpResult.setFlag(HttpResult.Flag_Fail);
            httpResult.setMsg(e.getMessage());
        }
        else{
            httpResult.setFlag(HttpResult.Flag_Fail);
            httpResult.setMsg("失败");
        }
//        if (G.isTest)
//            e.printStackTrace();
//        else
            logger.error(DateUtil.dateToString(new Date()),e);
        return httpResult;
    }


    private void finallyHandle() {
//        UserContextUtil.removeUser();
    }

    private void afterHandle(HttpResult object, ProceedingJoinPoint joinPoint) throws Exception{
        // 参数验证判断
//        if (getAnnotation(joinPoint, ParamVerify.class)){
//            Object[] args = joinPoint.getArgs();
//            BindingResult bindingResult = null;
//            for (Object arg : args) {
//                if ( arg instanceof BindingResult){
//                    bindingResult = (BindingResult) arg;
//                    if (bindingResult!=null&&bindingResult.hasErrors()){
//                        throw new RuntimeException("参数不对");
//                    }
//                    break;
//                }
//            }
//        }
    }

    private boolean getAnnotation(ProceedingJoinPoint joinPoint,Class<? extends Annotation> annotationClass) throws Exception{
        Class<?> targetClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] par=((MethodSignature) joinPoint.getSignature()).getParameterTypes();

        Method method = targetClass.getMethod(methodName, par);
        Annotation annotation = method.getAnnotation(annotationClass);
        return annotation!=null;
    }
}
