package com.meehoo.biz.core.basic.ro.security;


import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordRO extends IdRO {
    private String oldPw;
    private String newPw;
    private String confirmNewPw;
}
