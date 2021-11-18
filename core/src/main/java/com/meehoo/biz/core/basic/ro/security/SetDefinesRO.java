package com.meehoo.biz.core.basic.ro.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zc
 * @date 2020-12-17
 */
@Setter
@Getter
public class SetDefinesRO {
    private String roleId;

    private List<String> menuDefineIds;
}
