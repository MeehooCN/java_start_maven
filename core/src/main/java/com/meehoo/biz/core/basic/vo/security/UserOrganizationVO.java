package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StreamUtil;
import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.domain.security.UserOrganization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

/**
 * Created by CZ on 2018/2/25.
 */
@Setter
@Getter
@NoArgsConstructor
public class UserOrganizationVO {
    private String orgId;

    private String orgName;

    private List<String> roleNameList;

    private String location;

    public UserOrganizationVO(UserOrganization userOrganization) {
        Organization organization = userOrganization.getOrganization();
        if (BaseUtil.objectNotNull(organization)) {
            this.orgId = organization.getId();
            this.orgName = organization.getName();
        }
        userOrganization.getUserOrganizationRoleList().sort(Comparator.comparing(uor -> uor.getRole().getCode()));
        this.roleNameList = StreamUtil.extractFieldToList(userOrganization.getUserOrganizationRoleList(), uor -> uor.getRole().getName());
        this.location = organization.getProvince()+"/"+organization.getCity()+"/"+organization.getArea();
    }
}