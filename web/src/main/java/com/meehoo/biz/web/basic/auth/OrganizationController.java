package com.meehoo.biz.web.basic.auth;

import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.security.OrganizationRO;
import com.meehoo.biz.core.basic.service.security.IOrganizationService;
import com.meehoo.biz.core.basic.vo.security.OrganizationTreeTotalVO;
import com.meehoo.biz.core.basic.vo.security.OrganizationVO;
import com.meehoo.biz.web.basic.common.BaseController;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CZ on 2018/1/19.
 */
@Api(tags = "组织机构管理")
@RestController
@RequestMapping("/security/organization")
@AllArgsConstructor
public class OrganizationController extends BaseController<Organization, OrganizationVO> {

    private final IOrganizationService organizationService;


    @PostMapping("create")
    public HttpResult<String> create(@RequestBody OrganizationRO organizationRO) throws Exception {
        return HttpResult.success(organizationService.createOrUpdate( organizationRO));
    }

    /**
     * 查询所有机构
     * @return
     * @throws Exception
     */
    @PostMapping("listAllInTree")
    public HttpResult<OrganizationTreeTotalVO> listAllForAdmin() throws Exception {
        OrganizationTreeTotalVO organizationTreeTotalVO = organizationService.listAll();
        return HttpResult.success(organizationTreeTotalVO);
    }

    /**
     * 查询所有状态为启用的机构
     *
     * @return
     * @throws Exception
     */
    @GetMapping("listAllEnableForAdmin")
    public HttpResult<OrganizationTreeTotalVO> listAllEnableForAdmin() throws Exception {
        OrganizationTreeTotalVO organizationTreeTotalVO = organizationService.getAllOrganizationWithEnable();
        return HttpResult.success(organizationTreeTotalVO);
    }
}