package com.meehoo.biz.core.basic.handler;

import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.exception.NotLoginException;
import org.apache.shiro.SecurityUtils;

/**
 * @author zc
 * @date 2020-05-29
 */
public class UserManager {

    public static Object getCurrent(){
        return SecurityUtils.getSubject().getPrincipal();
    }

    public static Admin getCurrentAdmin()throws Exception{
        Object current = getCurrent();
        if (current instanceof Admin){
            return (Admin) current;
        }
        throw new NotLoginException();
    }

    public static User getCurrentUser()throws Exception{
        Object current = getCurrent();
        if (current instanceof User){
            return (User) current;
        }
        throw new NotLoginException();
    }

    public static String getCurrentOrgCode()throws Exception{
        Object current = getCurrent();
        if (current instanceof User){
            return ((User) current).getOrgCode();
        }
        if (current instanceof Admin){
            return ((Admin) current).getOrganizationCode();
        }
        throw new NotLoginException();
    }
}
