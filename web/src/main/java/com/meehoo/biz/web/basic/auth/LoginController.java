package com.meehoo.biz.web.basic.auth;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.annotation.UnAop;
import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.vo.security.AdminVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@Api(tags = "登录管理")
@Controller
@Slf4j
public class LoginController {
    private final Producer captchaProducer;
    @Value("${kaptcha.enable}")
    private String enableKaptcha;

    @Autowired
    public LoginController(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    /**
     * 获取验证码
     */
    @GetMapping(value = "getKaptchaImage")
    @UnAop
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        //request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //将验证码存到session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 异步校验验证码
     */
    @PostMapping("/checkLoginValidateCode")
    @ResponseBody
    public HttpResult checkLoginValidateCode(HttpServletRequest request, String validateCode) {
        String loginValidateCode = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
        if (loginValidateCode == null) {
            return HttpResult.fail("验证码已过期");
        } else if (!loginValidateCode.equals(validateCode)) {
            return HttpResult.fail("验证码错误");
        } else {
            return HttpResult.fail("验证码正确");
        }
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    @UnAop
    public HttpResult login(String userName, String password, String code, HttpServletRequest request) {
        //校验验证码
        if (!"false".equals(enableKaptcha)){
            Object attribute = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            Assert.notNull(attribute,"验证码已过期");
            String verificationCodeIn = String.valueOf(attribute);
            request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (StringUtil.stringIsNull(verificationCodeIn) || !verificationCodeIn.equals(code)) {
                throw new RuntimeException("验证码不正确");
            }
        }
        // 账号密码校验
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        String msg = "";
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            msg = "账号不存在！";
        } catch (DisabledAccountException e) {
            msg = "账号未启用！";
        } catch (IncorrectCredentialsException e) {
            msg = "密码错误！";
        } catch (Throwable e) {
            if (StringUtil.stringIsNull(e.getMessage())) {
                msg = "未知错误！";
            } else {
                msg = e.getMessage();
            }
        }
        log.info(msg);

        if (subject.isAuthenticated() && StringUtil.stringIsNull(msg)) {
            Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
            return HttpResult.success(new AdminVO(admin));
        } else {
            return HttpResult.fail(msg);
        }
    }

    @GetMapping("/403")
    public String unauthorized() {
        log.info("------没有权限-------");
        return "unauthorized";
    }
}