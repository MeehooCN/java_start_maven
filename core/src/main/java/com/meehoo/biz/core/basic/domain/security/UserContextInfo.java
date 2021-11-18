package com.meehoo.biz.core.basic.domain.security;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CZ on 2018/7/30.
 */
@Getter
@Setter
public class UserContextInfo {
    /**
     * 当前机构Id
     */
    private String currOrgId;

    /**
     * 当前机构名称
     */
    private String currOrgName;

    public String getCurrOrgId() {
        return currOrgId==null?"":currOrgId;
    }

    public String getCurrOrgName() {
        return currOrgName==null?"":currOrgName;
    }
}
