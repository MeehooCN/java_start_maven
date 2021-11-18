package com.meehoo.biz.core.basic.vo.bos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by CZ on 2018/7/27.
 */
@Getter
@Setter
public class MenuTreeVO {
    private List<MenuVO> children;
}
