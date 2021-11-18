package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.core.basic.ro.BaseEntityRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zc
 * @date 2019-04-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminToRoleRO extends BaseEntityRO {
    private String adminId;
    private List<String> roleIdList;
}
