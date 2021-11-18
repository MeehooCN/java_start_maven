package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.common.util.BaseUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by CZ on 2018/1/19.
 */
@Getter
@Setter
public class UserOrganizationRO {
    private String userId;

    private String organizationId;

//    public ControllerMessage checkFields() {
//        ControllerMessage controllerMessage = new ControllerMessage();
//        if (BaseUtil.stringIsNull(userId)) {
//            controllerMessage.setFailMsg("用户id不能为空");
//        } else if (BaseUtil.stringIsNull(organizationId)) {
//            controllerMessage.setFailMsg("用户所属机构id不能为空");
//        }
//        return controllerMessage;
//    }
}