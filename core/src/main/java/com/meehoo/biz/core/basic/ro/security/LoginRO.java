package com.meehoo.biz.core.basic.ro.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by CZ on 2018/9/5.
 */
@Data
public class LoginRO {
    private String username;
    private String password;
    private String deviceId;
}
