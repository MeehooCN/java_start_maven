package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.bos.PageRO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zc
 * @date 2021-06-02
 */
@Setter
@Getter
public class AdminSearchRO extends PageRO {
    private String name;

    private String organizationCode;

    private String userName;
}
