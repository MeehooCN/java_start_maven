package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.security.IOrganizationDao;
import com.meehoo.biz.core.basic.dao.security.IUserOrganizationDao;
import com.meehoo.biz.core.basic.domain.security.Organization;
import com.meehoo.biz.core.basic.ro.security.OrganizationRO;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.service.common.ICommonService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.security.OrganizationTreeTotalVO;
import com.meehoo.biz.core.basic.vo.security.OrganizationTreeVO;
import com.meehoo.biz.core.basic.vo.security.OrganizationVO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by CZ on 2018/1/17.
 */
@Service
public class OrganizationServiceImpl extends BaseService implements IOrganizationService {

    private final IOrganizationDao organizationDao;

    private final IUserOrganizationDao userOrganizationDao;

    private final ICommonService commonService;

    @Autowired
    public OrganizationServiceImpl(IOrganizationDao organizationDao, IUserOrganizationDao userOrganizationDao, ICommonService commonService) {
        this.organizationDao = organizationDao;
        this.userOrganizationDao = userOrganizationDao;
        this.commonService = commonService;
    }

    @Transactional
    @Override
    public String createOrUpdate(OrganizationRO organizationRO) throws Exception {
        Organization thisOrg;
        if (StringUtil.stringNotNull(organizationRO.getId())) {
            thisOrg = this.queryById(Organization.class, organizationRO.getId());
            thisOrg.setName(organizationRO.getName());
            thisOrg.setAddress(organizationRO.getAddress());
            thisOrg.setContactPerson(organizationRO.getContactPerson());
            thisOrg.setContactPhone(organizationRO.getContactPhone());
            thisOrg.setProOrgType(organizationRO.getProOrgType());
            thisOrg.setDescription(organizationRO.getDescription());
            thisOrg.setIsDefault(organizationRO.getIsDelete());
        } else {
            thisOrg = new Organization();
            thisOrg.setCode(getOrgCode(organizationRO.getParentOrgCode(),organizationRO.getParentOrgId()));
            thisOrg.setName(organizationRO.getName());
            thisOrg.setAddress(organizationRO.getAddress());
            thisOrg.setContactPerson(organizationRO.getContactPerson());
            thisOrg.setContactPhone(organizationRO.getContactPhone());
            thisOrg.setProOrgType(organizationRO.getProOrgType());
            thisOrg.setDescription(organizationRO.getDescription());
            thisOrg.setIsDelete(organizationRO.getIsDelete());
            Organization parentOrg = StringUtil.stringNotNull(organizationRO.getParentOrgId()) ?
                    this.queryById(Organization.class, organizationRO.getParentOrgId()) : null;
            if (BaseUtil.objectNotNull(parentOrg)) {
                thisOrg.setParentOrg(parentOrg).setGrade(parentOrg.getGrade() + 1).setIsLeaf(Organization.ISLeaf_YES);
            } else {
                thisOrg.setGrade(Organization.LEVEL_TOP).setIsLeaf(Organization.ISLeaf_NO);
            }
        }
        commonService.setProvinceCityAreaAndAddress(thisOrg, organizationRO);
        this.saveOrUpdate(thisOrg);
        return thisOrg.getId();
    }

    // 每三位表示一个层级
    private String getOrgCode(String parentOrgCode,String parentOrgId) {
        int num = 0;
        if (StringUtil.stringNotNull(parentOrgCode)){
            // 有父机构
            // 查询父机构下有多少子机构
            List<Organization> sub = organizationDao.findByParentOrgId(parentOrgId);
            if (BaseUtil.collectionNotNull(sub))
                num = sub.size();
            return parentOrgCode+createCodeByNum(num);
        }else{
            // 没有父机构
            List<Organization> listRoot = organizationDao.listRoot();
            if (BaseUtil.collectionNotNull(listRoot))
                num = listRoot.size();
            return createCodeByNum(num);
        }
    }

    private String createCodeByNum(int num){
        num++;
        if (num < 10){
            return "00"+num;
        }else if (num < 100){
            return "0"+num;
        }else if (num < 1000){
            return ""+num;
        }else{
            throw new RuntimeException("子机构数量超过1000");
        }
    }


    @Override
    public OrganizationTreeTotalVO listAll(){
        OrganizationTreeTotalVO orgTotalTreeVO = new OrganizationTreeTotalVO();
        List<Organization> topOrgList = new ArrayList<>();
        List<Organization> orgList = organizationDao.findByIsDelete(0);
        for (Organization org : orgList) {
            Organization parentOrg = org.getParentOrg();
            if (parentOrg == null)
                topOrgList.add(org);
            else
                parentOrg.getSubOrgList().add(org);
        }
        orgTotalTreeVO.setTotal((long) orgList.size());
        orgTotalTreeVO.setChildren(VOUtil.convertDomainListToTempList(topOrgList, OrganizationTreeVO.class));
        return orgTotalTreeVO;
    }

    @Override
    public List<OrganizationVO> getSubOrgList(String parentOrgId) {
        List<Organization> orgList;
        if (StringUtil.stringNotNull(parentOrgId)) {
            orgList = organizationDao.findByParentOrgId(parentOrgId);
        } else {
            orgList = new ArrayList<>(organizationDao.findTopOrg());
        }
        return VOUtil.convertDomainListToTempList(orgList, OrganizationVO.class);
    }

    @Override
    public List<Organization> getAllSubOrg(String parentOrgCode){
        DetachedCriteria dc = DetachedCriteria.forClass(Organization.class);
        dc.add(Restrictions.like("code",parentOrgCode, MatchMode.START));
        return list(dc);
    }

    @Override
    public List<OrganizationVO> queryByUserId(String userId){
        List<String> orgIdList = allSubOrgIdListByUserId(userId);
        orgIdList.add(userOrganizationDao.findByUserId(userId).get(0).getOrganization().getId());
        List<Organization> orgList = organizationDao.queryByIdList(orgIdList);
        return VOUtil.convertDomainListToTempList(orgList, OrganizationVO.class);
    }

    @Override
    public List<OrganizationVO> listOrgAndSubOrg(String orgId){
        List<Organization> orgList = new ArrayList<>();
        Organization organization = this.queryById(Organization.class, orgId);
        orgList.add(organization);
        List<Organization> orgSubList = organizationDao.findByParentOrgId(orgId);
        orgList.addAll(orgSubList);

        return VOUtil.convertDomainListToTempList(orgList, OrganizationVO.class);
    }

    @Override
    public boolean delete( String id){
        boolean result = false;
        try {
            organizationDao.deleteById(id);
            result =  true;
        } catch (org.springframework.orm.jpa.JpaSystemException e) {
            System.out.println(e.toString());
        }
        return result;
    }

    @Override
    public Organization queryById(String orgId){
        return organizationDao.queryById(orgId);
    }

    @Override
    public List<String> allSubOrgIdListByUserId(String userId) {
        if (StringUtil.stringNotNull(userId)) {
            //查询当前机构下子机构的id
            String sql ="select t3.id from(" +
                    "select t1.id,\n" +
                    "if(FIND_IN_SET(parentOrg_id,@pids)>0,@pids\\:=concat(@pids,',',id),0)as isChild\n" +
                    "from(\n" +
                    "(select id,parentOrg_id from sec_org order by grade)t1,\n" +
                    "(select @pids\\:=(select organization_id from sec_user_organization where user_id=?1))t2\n" +
                    "))t3\n" +
                    "where t3.isChild !=0";
            return baseDao.queryBySQL(sql, userId);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Organization> listRoot() {
        return organizationDao.listRoot();
    }

    @Override
    public OrganizationTreeTotalVO getAllOrganizationWithEnable(){
        OrganizationTreeTotalVO orgTotalTreeVO = new OrganizationTreeTotalVO();
        List<Organization> topOrgList = new ArrayList<>();
        List<Organization> orgList = organizationDao.findAll();
        for (Organization org : orgList) {
            if(org.getIsDelete() == 1){
                continue;
            }
            Organization parentOrg = org.getParentOrg();
            if (parentOrg == null) {
                topOrgList.add(org);
            } else
                parentOrg.getSubOrgList().add(org);
        }
        orgTotalTreeVO.setTotal((long) orgList.size());
        orgTotalTreeVO.setChildren(VOUtil.convertDomainListToTempList(topOrgList, OrganizationTreeVO.class));
        return orgTotalTreeVO;
    }
}
