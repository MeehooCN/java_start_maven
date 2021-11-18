package com.meehoo.biz.core.basic.vo.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meehoo.biz.core.basic.vo.bos.MenuVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 授权菜单视图
 * Created by CZ on 2017/10/25.
 */
@Getter
@Setter
public class AuthMenuTreeVO {
    private List<MenuVO> children;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> checked;
}
