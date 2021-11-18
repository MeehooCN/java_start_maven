package com.meehoo.biz.web.basic.auth;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.param.HttpResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * Created by xg on 2020/12/10.
 */
@RestController
@RequestMapping("/security/kaptcha")
public class KaptchaController {
    @Autowired
    DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "生成验证码")
    @RequestMapping(value = "getKaptchaImage",method = RequestMethod.GET)
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
            // 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    //    @ApiOperation(value = "校对验证码")
//    @PostMapping("/checkVerificationCode")
    public HttpResult checkVerificationCode(@RequestParam(value = "verificationCode") String verificationCode, HttpServletRequest httpServletRequest) {
        String verificationCodeIn = (String) httpServletRequest.getSession().getAttribute("verificationCode");
        httpServletRequest.getSession().removeAttribute("verificationCode");
        if (StringUtil.stringIsNull(verificationCodeIn) || !verificationCodeIn.equals(verificationCode)) {
            return HttpResult.fail("验证不通过");
        }
        return HttpResult.success();
    }
}
