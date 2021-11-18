package com.meehoo.biz.core.basic.hierarchy;

import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zc
 * @date 2020-04-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HierarchyDomainRO extends IdRO {
    private String parentId;

//    private String parentCode;
}
