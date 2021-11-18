package com.meehoo.biz.core.basic.vo;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zc
 * @date 2019-09-03
 */
@Data
@NoArgsConstructor
public class IdEntityVO {
    private String id;

    public IdEntityVO(IdEntity idEntity) {
        this.id = idEntity.getId();
    }
}
