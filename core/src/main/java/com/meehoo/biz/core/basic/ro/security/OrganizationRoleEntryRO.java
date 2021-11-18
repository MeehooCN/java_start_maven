package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.common.util.BaseUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by CZ on 2018/1/26.
 */
@Getter
@Setter
public class OrganizationRoleEntryRO {
    private String organizationId;

    private List<String> roleIdList;

//    public ControllerMessage checkFields() {
//        ControllerMessage controllerMessage = new ControllerMessage();
//        if(BaseUtil.stringIsNull(organizationId)){
//            return controllerMessage.setFailMsg("请选择用户所属的机构");
//        }
//        return controllerMessage;
//    }
}
