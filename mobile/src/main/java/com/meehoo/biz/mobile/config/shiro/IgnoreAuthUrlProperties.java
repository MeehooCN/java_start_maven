package com.meehoo.biz.mobile.config.shiro;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zc
 * @date 2020-04-16
 */

@Component
//@ConfigurationProperties(prefix = "spring.shiro")
@Data
public class IgnoreAuthUrlProperties {
    List<String> ignoreAuthUrl;

    public IgnoreAuthUrlProperties() {
        ignoreAuthUrl = new ArrayList<>();
        ignoreAuthUrl.add("/static/**");
        ignoreAuthUrl.add("/resources/**");
        ignoreAuthUrl.add("/doc.html");
        ignoreAuthUrl.add("/login");
        ignoreAuthUrl.add("/remoteLogin");
        ignoreAuthUrl.add("/");
        ignoreAuthUrl.add("/out/evaluate/**");
//        ignoreAuthUrl.add("/biz/**");
    }
}
