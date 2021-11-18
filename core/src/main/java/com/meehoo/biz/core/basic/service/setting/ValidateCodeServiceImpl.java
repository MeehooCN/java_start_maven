package com.meehoo.biz.core.basic.service.setting;

import com.meehoo.biz.core.basic.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

/**
 * Created by wanghan on 2016-05-09.
 */

@Service
@Transactional
public class ValidateCodeServiceImpl extends BaseService implements IValidateCodeService {

//    private final IValidateCodeDao validateCodeDao;

//    @Autowired
//    public ValidateCodeServiceImpl(IValidateCodeDao validateCodeDao) {
//        this.validateCodeDao = validateCodeDao;
//    }

    @Override
    public boolean checkCode(String code, String phone, Date date) throws Exception {
//        List<ValidateCode> list = validateCodeDao.findValidateCode(phone,date);
//        if(BaseUtil.listNotNull(list)){
//            ValidateCode validateCode = list.get(0);
//            return validateCode.getCode().equals(code);
//        }else{
            return false;
//        }
    }

    @Override
    public void sendCode(String phone) throws Exception {
        //随机生成6位验证码
        int max = 1000000;
        int min = 100000;
        Random random = new Random();
        int code = random.nextInt(max) % (max - min + 1) + min;

        //发送短信
//        SMS sms =  new SMS(SMS.appId,SMS.appAccountId,SMS.appAuthToken);
//        CCPRestSmsSDK restAPI = sms.getRestAPI();
//       // HashMap result = SMS.senVCode(phone,restAPI, String.valueOf(code), "1");
//
//        ValidateCode validateCode = new ValidateCode();
//        validateCode.setPhone(phone);
//        validateCode.setCode(String.valueOf(code));
//        Calendar cal = Calendar.getInstance();
//        validateCode.setTime(cal.getTime());
//        cal.add(Calendar.MINUTE, 1);
//        validateCode.setExpiredTime(cal.getTime());

//        validateCodeDao.save(validateCode);
    }
}
