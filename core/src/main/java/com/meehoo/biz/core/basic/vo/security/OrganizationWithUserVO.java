package com.meehoo.biz.core.basic.vo.security;

import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.vo.common.AddressEntityVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zc
 * @date 2019-04-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationWithUserVO extends AddressEntityVO {
    private String orgName;
    /**
     * 机构类型
     */
//    private ProOrgTypeVO proOrgTypeVO;

    /**
     * 是否系统预设
     * 0  否
     * 1  是
     */
    private int isDefault;

    private String isDefaultShow;

    private String contactPhone; // 联系电话

    private String description;

    private List<UserVO> userVOList;

    public OrganizationWithUserVO(Organization organization){
        super(organization);
        this.orgName=organization.getName();
        this.contactPhone = organization.getContactPhone();
        this.address = organization.getAddress();
        this.description = organization.getDescription();
        this.isDefault = organization.getIsDefault();
        this.isDefaultShow = this.isDefault == Organization.ISDefault_YES ? "是" : "否";
//        this.proOrgTypeVO = new ProOrgTypeVO(organization.getProOrgType());
    }
}
