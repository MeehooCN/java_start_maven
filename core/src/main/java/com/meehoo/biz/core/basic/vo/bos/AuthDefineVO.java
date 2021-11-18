package com.meehoo.biz.core.basic.vo.bos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zc
 * @date 2020-12-31
 */
@Setter
@Getter
public class AuthDefineVO {
    private List<MenuWithDefineVO> defineVOList;

    private List<String> authList;
}
