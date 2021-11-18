package com.meehoo.biz.mobile.basic;

import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.security.LoginRO;
import com.meehoo.biz.core.basic.service.security.ILoginRecordService;
import com.meehoo.biz.mobile.param.TokenRO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zc
 * @date 2019-08-01
 */
@RestController
@RequestMapping("login")
@Api("手机端登录")
public class LoginController {
    private final ILoginRecordService loginRecordService;

    @Autowired
    public LoginController(ILoginRecordService loginRecordService) {
        this.loginRecordService = loginRecordService;
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public HttpResult<String> login(@RequestBody LoginRO ro) throws Exception{
        String token = loginRecordService.login(ro.getUsername(), ro.getPassword());
        return HttpResult.success(token);
    }

    @ApiOperation("退出")
    @PostMapping("exit")
    public HttpResult exit(@RequestBody TokenRO ro) throws Exception{
        loginRecordService.exit(ro.getToken());
        return HttpResult.success();
    }

//    @ApiOperation("登录")
//    @PostMapping("loginForUser")
//    public HttpResult<UserVO> loginForUser(@RequestBody LoginRO ro) throws Exception{
//        User user = loginRecordService.login1(ro.getUserName(), ro.getPwd());
//        return HttpResult.success(new UserVO(user));
//    }

    @ApiOperation("登录")
    @PostMapping("loginForUser")
    public HttpResult<String> loginForUser(String userName, String pwd) throws Exception{
        User user = loginRecordService.login1(userName, pwd);
        return HttpResult.success();
    }


}
