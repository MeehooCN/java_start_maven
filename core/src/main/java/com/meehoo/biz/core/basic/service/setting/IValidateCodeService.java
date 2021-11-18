package com.meehoo.biz.core.basic.service.setting;


import com.meehoo.biz.core.basic.service.IBaseService;

import java.util.Date;

/**
 * Created by wanghan on 2016-05-09.
 * 短信验证码
 */

public interface IValidateCodeService extends IBaseService {

    /**
     * 验证手机号码有效的验证码
     * @param phone
     * @param date
     * @return
     * @throws Exception
     */
    boolean checkCode(String code, String phone, Date date) throws Exception;

    /**
     * 发送验证码
     * @param phone
     * @throws Exception
     */
    void sendCode(String phone) throws Exception;

}
