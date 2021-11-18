package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by CZ on 2017/10/20.
 */
@Setter
@Getter
public class UserRO extends IdRO {
    private String userName;

    private String number;

    private String name;

    private String phone;

    private String email;

    private List<String> orgIdList;

    private String title;

    private String staffId;

    private String orgId;

    private String orgName;

    private String orgCode;

    private int isForceMerge=0;//是否强制合并用户所属机构,0-否，1-是
}
