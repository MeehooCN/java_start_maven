package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.core.basic.domain.security.MenuDefine;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zc
 * @date 2020-12-17
 */
@Setter
@Getter
public class MenuDefineVO extends IdEntityVO {
    private String name;

    private int isDelete;


    public MenuDefineVO(MenuDefine menuDefine) {
        super(menuDefine);
        this.name = menuDefine.getName();
        this.isDelete = menuDefine.getIsDelete();
    }
}
