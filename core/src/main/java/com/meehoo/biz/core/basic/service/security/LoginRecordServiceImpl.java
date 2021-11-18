package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.core.basic.dao.security.IUserDao;
import com.meehoo.biz.core.basic.domain.bos.LoginRecord;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.domain.security.UserLoginRecord;
import com.meehoo.biz.core.basic.exception.PwdNotRightException;
import com.meehoo.biz.core.basic.service.common.BaseServicePlus;
import com.meehoo.biz.core.basic.util.RedisTemplateService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author zc
 * @date 2019-08-01
 */
@Service
@Transactional
public class LoginRecordServiceImpl extends BaseServicePlus implements ILoginRecordService{
    public static final long expireLimit = 2 * 60 * 60 * 1000;
    private int type=1;

    private final IUserDao userDao;
    private final RedisTemplateService redisTemplateService;

    @Autowired
    public LoginRecordServiceImpl(IUserDao userDao, RedisTemplateService redisTemplateService) {
        this.userDao = userDao;
        this.redisTemplateService = redisTemplateService;
    }

    @Override
    public String login(String userName, String pwd) throws Exception{
        User user = userDao.getByUserNameAndPassword(userName, pwd);
        if (user == null)
            throw new RuntimeException("账号密码错误");

        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expireLimit); // 过期时间
        if (type==0){
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            LoginRecord loginRecord = new LoginRecord(user);

            loginRecord.setLoginTime(now);

            loginRecord.setExpireTime(expireTime);
            redisTemplateService.set(token,loginRecord);
            return token;
        }else{
            UserLoginRecord loginRecord = new UserLoginRecord();
            loginRecord.setLoginTime(now);
            loginRecord.setAvailableTime(expireTime);
            loginRecord.setUserId(user.getId());
            save(loginRecord);
            return loginRecord.getId();
        }
    }

    @Override
    public boolean isExpired(@NonNull String token) throws Exception{
        if (type==0){

        }else{
            Date now = new Date();
            UserLoginRecord loginRecord = queryById(UserLoginRecord.class, token);
            if (loginRecord!=null&&now.before(loginRecord.getAvailableTime())){
                loginRecord.setAvailableTime(new Date(now.getTime() + expireLimit));
                update(loginRecord);
                return false;
            }
        }
        return true;
    }

    @Override
    public void exit(String token) throws Exception {
        Date now = new Date();
        LoginRecord record = redisTemplateService.get(token, LoginRecord.class);
        if (record !=null){
            record.setExpireTime(now);
            redisTemplateService.set(token,record);
        }
    }

    @Override
    public User login1(String userName, String pwd) throws Exception {
        User user = userDao.getByUserNameAndPassword(userName, pwd);
        if (user == null)
            throw new PwdNotRightException();
        return user;
    }
}
