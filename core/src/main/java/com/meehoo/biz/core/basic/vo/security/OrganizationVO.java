package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.vo.common.AddressEntityVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by CZ on 2018/1/19.
 */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class OrganizationVO extends AddressEntityVO {
    private String orgName;

    /**
     * 机构类型
     */
    private String proOrgType;

    /**
     * 是否系统预设
     * 0  否
     * 1  是
     */
    private int isDefault;

    private String isDefaultShow;

    private String parentOrgId;

    private String parentOrgName;

    private String contactPerson; // 联系人

    private String contactPhone; // 联系电话

    private String description;

    public OrganizationVO(Organization organization) {
        super(organization);
        this.orgName=organization.getName();
        this.contactPerson = organization.getContactPerson();
        this.contactPhone = organization.getContactPhone();
        this.address = organization.getAddress();
        this.description = organization.getDescription();
        this.isDefault = organization.getIsDefault();
        this.isDefaultShow = this.isDefault == Organization.ISDefault_YES ? "是" : "否";
        if (organization.getParentOrg()!=null){
            this.parentOrgId = organization.getParentOrg().getId();
            this.parentOrgName =organization.getParentOrg().getName();
        }
        this.proOrgType = organization.getProOrgType();
    }
}