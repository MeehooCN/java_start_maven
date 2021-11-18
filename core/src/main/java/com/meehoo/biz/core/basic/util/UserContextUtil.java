package com.meehoo.biz.core.basic.util;


import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.exception.NotLoginException;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Optional;

public class UserContextUtil {

    //用于安卓/PC端登录后保存用户信息
    private static final ThreadLocal<Object> UserContext = new ThreadLocal<>();


    public static Object getCurrentUser() {
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
//        if (authentication == null) {
            return null;
//        }
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            return UserContext.get();
//        }
//        return authentication.getPrincipal();
    }

    public static Admin getAdmin() {
        Admin admin = null;
        Object object = getCurrentUser();
        if (object instanceof Admin) {
            admin = (Admin) object;
        }
        if (admin == null){
            throw new NotLoginException();
        }
        return admin;
    }

    public static User getUser() {
        User user;
        Object object = getCurrentUser();
        user = Optional.ofNullable(object)
                .map(obj -> {
                    if (obj instanceof User) {
                        return (User) obj;
                    } else if (obj instanceof Admin) {
                        Admin admin = (Admin) obj;
                        User re = new User();
                        re.setId(admin.getId());
                        re.setName(admin.getUserName());
                        return re;
                    } else {
                        return null;
                    }
                })
                .orElse(null);
//        if (user == null){
//            throw new NotLoginException();
//        }
        return user;
    }

    /**
     * 在Controller方法执行前调用，如过滤器、AOP等
     *
     * @param user
     */
    public static void storeUser(User user) {
        UserContext.set(user);
    }

    /**
     * 在Controller方法执行完毕后调用，如过滤器、AOP等
     */
    public static void removeUser() {
        UserContext.remove();
    }



//    // 保存用户选择的项目信息
//    private static final Map<String,String> userProjectMap = new ConcurrentHashMap<>();
//
//    public static String getProject(){
//        User user = getUser();
//        if (userProjectMap.containsKey(user.getId())){
//            return userProjectMap.get(user.getId());
//        }else{
////            if (G.isTest){
////                return "402883286a534e6f016a537785750000";
////            }else
//                throw new RuntimeException("当前用户没有选择项目");
//        }
//    }
//
//    public static void setProject(String projectId){
//        User user = getUser();
//        userProjectMap.put(user.getId(),projectId);
//    }
}
