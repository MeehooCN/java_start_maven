package com.meehoo.biz.core.basic.vo.security;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



/**
 * Created by CZ on 2017/10/19.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserVO extends IdEntityVO{

    private String code;

    private String number;

    private String userName;

    private String name;

    private String phone;

    private String email;

    private String staffId;

    private String title;

    private Integer status;

    private String statusShow;

    private List<OrganizationVO> organizationVOList;//用户所属的机构

    private String organizationName;

    private String organizationId;

    private String organizationCode;

    private String roleName;

    private String roleId;

    public UserVO(User user) {
        super(user);
        this.code = user.getCode();
        this.number = user.getCode();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.phone =user.getPhone();
        this.email = user.getEmail();
        this.title = user.getTitle();
        status = user.getStatus();
        this.organizationId = user.getOrgId();
        this.organizationName = user.getOrgName();
        this.organizationCode = user.getOrgCode();
        if (status == User.STATUS_ENABLE){
            statusShow = "启用";
        }else{
            statusShow = "禁用";
        }
        this.roleName = user.getRoleName();
        this.roleId = user.getRoleId();
    }
}
