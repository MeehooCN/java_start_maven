package com.meehoo.biz.core.basic.vo;

import com.meehoo.biz.core.basic.domain.BaseEntity;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by CZ on 2017/11/1.
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class BaseEntityVO extends IdEntityVO {
    protected String name;
    protected String code;
    protected int isDelete;
    protected String enableStr;
//    protected String statusShow;

    protected BaseEntityVO(BaseEntity baseEntity) {
        super(baseEntity);
        this.name = baseEntity.getName();
        this.code = baseEntity.getCode();
        this.isDelete = baseEntity.getIsDelete();
        this.enableStr = baseEntity.getEnable() == 1 ? "启用" : "禁用";
    }
}
