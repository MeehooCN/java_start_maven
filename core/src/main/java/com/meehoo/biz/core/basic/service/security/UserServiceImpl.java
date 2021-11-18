package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.common.util.*;
import com.meehoo.biz.core.basic.dao.security.IUserDao;
import com.meehoo.biz.core.basic.dao.security.IUserOrganizationDao;
import com.meehoo.biz.core.basic.domain.security.*;
import com.meehoo.biz.core.basic.handler.UserManager;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.bos.PageRO;
import com.meehoo.biz.core.basic.ro.bos.SearchConditionListRO;
import com.meehoo.biz.core.basic.ro.security.OrganizationRoleRO;
import com.meehoo.biz.core.basic.ro.security.UserRO;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.service.common.ICommonService;
import com.meehoo.biz.core.basic.util.ReflectUtil;
import com.meehoo.biz.core.basic.util.SpringUtil;
import com.meehoo.biz.core.basic.util.UserContextUtil;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.security.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by CZ on 2017/10/19.
 */
@Service
@Transactional
public class UserServiceImpl extends BaseService implements IUserService {
    private final IUserDao userDao;
    private final IUserOrganizationDao userOrganizationDao;
    private final ICommonService commonService;
    private final IRoleService roleService;

    @Autowired
    public UserServiceImpl(IUserDao userDao, IUserOrganizationDao userOrganizationDao,
                           ICommonService commonService, IRoleService roleService) {
        this.userDao = userDao;
        this.userOrganizationDao = userOrganizationDao;
        this.commonService = commonService;
        this.roleService = roleService;
    }

    @Override
    public User getUserByName(String userName) throws Exception {
        List<User> userList = userDao.queryByUserName(userName);
        if (BaseUtil.collectionNotNull(userList)) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserByNumber(String userNumber) throws Exception {
        List<User> userList = userDao.queryByNumber(userNumber);
        if (BaseUtil.collectionNotNull(userList)) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public void saveOrganizationRoleLink(OrganizationRoleRO organizationRoleRO) throws Exception {
        UserOrganization userOrg = userOrganizationDao.findByUserIdAndOrganizationId(organizationRoleRO.getUserId(), organizationRoleRO.getOrganizationId());
        if (BaseUtil.objectNotNull(userOrg)) {
            List<UserOrganizationRole> userOrgRoleList = userOrg.getUserOrganizationRoleList();
            List<UserOrganizationRole> needSaveUserOrgRoleList = new ArrayList<>();
            if (BaseUtil.collectionNotNull(organizationRoleRO.getRoleIdList())) {
                Map<String, UserOrganizationRole> userOrgRoleMap = StreamUtil.convertCollectionToMap(userOrgRoleList, usOrgRole -> usOrgRole.getRole().getId());
                for (String roleId : organizationRoleRO.getRoleIdList()) {
                    UserOrganizationRole userOrgRole = userOrgRoleMap.get(roleId);
                    userOrgRole = userOrgRole == null ? new UserOrganizationRole() : userOrgRole;
                    userOrgRole.setUserOrganization(userOrg).setRole(ReflectUtil.setDomainId(new Role(), roleId));
                    needSaveUserOrgRoleList.add(userOrgRole);
                }
            }
            this.mergeEntryList(userOrgRoleList, needSaveUserOrgRoleList);
        } else {
            throw new NullPointerException("该用户不属于id为:" + organizationRoleRO.getOrganizationId() + "的机构");
        }
    }

    @Override
    public UserVO getUserOrgList(String userId) throws Exception {
        User user = this.queryById(User.class, userId);
        UserVO userVO = user == null ? new UserVO() : new UserVO(user);
        List<UserOrganization> userOrganizationList = userOrganizationDao.findByUserId(userId);
        List<Organization> orgList = StreamUtil.extractFieldToList(userOrganizationList, UserOrganization::getOrganization);
        orgList.sort(Comparator.comparingInt(Organization::getGrade));
        if (BaseUtil.collectionNotNull(orgList)) {
            List<OrganizationVO> organizationVOList = VOUtil.convertDomainListToTempList(orgList, OrganizationVO.class);
            userVO.setOrganizationVOList(organizationVOList);
        }
        return userVO;
    }

    @Override
    public List<UserOrganizationVO> getUserOrgAndRoleList(String userId) throws Exception {
        List<UserOrganization> userOrganizationList = userOrganizationDao.findByUserId(userId);
        userOrganizationList.sort(Comparator.comparingInt(userOrg -> userOrg.getOrganization().getGrade()));
        return VOUtil.convertDomainListToTempList(userOrganizationList, UserOrganizationVO.class);
    }

    @Override
    public List<RoleVO> getUserHasRoleList(String userId, String organizationId) throws Exception {
        List<Role> roleList = roleService.listAll();
        if (BaseUtil.collectionNotNull(roleList)) {
            List<RoleVO> roleVOList = VOUtil.convertDomainListToTempList(roleList, RoleVO.class);
            Map<String, RoleVO> roleVOMap = roleVOList.stream().collect(Collectors.toMap(RoleVO::getId,e->e));
            List<Role> userHasRoleList = roleService.getRolesByUserAndOrg(userId, organizationId);
            if (BaseUtil.collectionNotNull(userHasRoleList)) {
                for (Role role : userHasRoleList) {
                    RoleVO roleVO = roleVOMap.get(role.getId());
                    roleVO.setUserHas(RoleVO.USERHAS_YES);
                }
            }
            return roleVOList;
        }
        return new ArrayList<>(0);
    }

    @Override
    public String createSaveUser(UserRO userRO) throws Exception {
        // 判断用户名是否重复
        List<User> users = userDao.queryByUserName(userRO.getUserName());
        if (users.size()>0){
            throw new RuntimeException("新建用户姓名重复");
        }
        // 判断电话是否重复
//        User oldUser = userDao.getByPhone(userRO.getPhone());
//        if (oldUser != null){
//            throw new RuntimeException("新建用户电话重复");
//        }
        User user = new User();
        boolean hasNoNumber = !StringUtil.stringNotNull(userRO.getNumber());
        if (hasNoNumber) {
            user.setCode(commonService.getBizObjectSerialNumber("User"));
        } else {
            user.setCode(userRO.getNumber());
        }
        user.setName(userRO.getName());
        user.setUserName(userRO.getUserName());
        user.setPhone(userRO.getPhone());
        user.setPassword(MD5Encrypt.EncryptPassword("123456"));
        user.setEmail(userRO.getEmail());
        user.setOrgId(userRO.getOrgId());
        user.setOrgName(userRO.getOrgName());
        user.setOrgCode(userRO.getOrgCode());
        if (UserContextUtil.getCurrentUser() instanceof Admin){
            user.setStatus(User.STATUS_ENABLE);
        }else{
            user.setStatus(User.STATUS_FORBID);
        }
//        user.setBlueToothAddress(userRO.getBlueToothAddress());
        user.setTitle(userRO.getTitle());
        this.save(user);
        //回传userId
        userRO.setId(user.getId());
        //保存用户与所属机构的关联
        saveOrganizationLink(user, userRO.getOrgIdList(), userRO.getIsForceMerge() == 1);
        //创建腾讯云账号
        //sigService.accountImport(user);
        //创建全景用户
//        this.createKrpanoUser(userRO.getPhone(), userRO.getUserName(), userRO.getName(), user.getId());
        return user.getId();
    }

    @Override
    public void createKrpanoUser(String phone, String username, String name, String id) throws Exception {
        String now = DateUtil.timeToString(new Date());
        String hql = "insert into u_user(phone,openid,username,nickname,password,level,limit_num,create_time,last_time,state,amount)"
                + "values('" + phone + "','" +
                id + "','" +
                username + "','" +
                name +
                "','14e1b600b1fd579f47433b88e8d85291',1,0," +
                "'" + now + "','" + now + "',0,50000" + ")";
        baseDao.updateBySql(hql);
    }

    @Override
    public void updateKrpanoUser(String phone, String name, String id) throws Exception {
        String hql = " update u_user set nickname='" + name + "',phone='" + phone
                + "' where openid='" + id + "'";
        int affectedRows = baseDao.updateBySql(hql);
        if(affectedRows==0){
            User user = this.queryById(User.class,id);
            if(user!=null){
                createKrpanoUser(user.getPhone(),user.getUserName(),user.getName(),user.getId());
            }
        }
    }

    @Override
    public Map<String,List<OrganizationWithUserVO>> classifyByOrganization() throws Exception {
        Map<String,List<OrganizationWithUserVO>> result = new HashMap<>();
        // 查询所有机构,按机构类型分类
        List<Organization> organizations = listAll(Organization.class, new ArrayList<>());
        organizations.stream().forEach(e->{
//            String projectType = e.getProOrgType().getName();
//            if (result.containsKey(projectType)){
//                result.get(projectType).add(new OrganizationWithUserVO(e));
//            }else{
//                List<OrganizationWithUserVO> data = new ArrayList<>();
//                OrganizationWithUserVO organizationWithUserVO = new OrganizationWithUserVO(e);
//                data.add(organizationWithUserVO);
//                result.put(projectType,data);
//            }
        });


        // 查询所有机构内人员，并排序
        List<UserOrganization> userOrganizationList = listAll(UserOrganization.class, new ArrayList<>());

        // 按机构类型和机构分类

        userOrganizationList.stream().forEach(e->{
            Organization organization = e.getOrganization();
//            String projectType = organization.getProOrgType().getName();
//            String id = organization.getId();
//
//            if (result.containsKey(projectType)){
//                List<OrganizationWithUserVO> organizationWithUserVOS = result.get(projectType);
//                for (OrganizationWithUserVO organizationWithUserVO:organizationWithUserVOS){
//                    if (id.equals(organizationWithUserVO.getId())){
//                        List<UserVO> userVOList = organizationWithUserVO.getUserVOList();
//                        if (userVOList == null){
//                            userVOList = new ArrayList<>();
//                            organizationWithUserVO.setUserVOList(userVOList);
//                        }
//                        UserVO userVO = new UserVO(e.getUser());
//                        userVO.setOrganizationName(organization.getName());
////                        List<OrganizationVO> organizationVOList = new ArrayList<>();
////                        organizationVOList.add(new OrganizationVO(organization));
////                        userVO.setOrganizationVOList(organizationVOList);
//                        userVO.setOrganizationId(organization.getId());
//                        userVOList.add(userVO);
//                        if (e.getUserOrganizationRoleList().size()>0){
//                            userVO.setRoleName(e.getUserOrganizationRoleList().get(0).getRole().getName());
//                        }
//                        break;
//                    }
//                }
//            }
        });
        return result;
    }

    @Override
    public String updateSaveUser(UserRO userRO) throws Exception {
        String msg =null;
        User user;
        if (StringUtil.stringNotNull(userRO.getId())) {
            user = this.queryById(User.class, userRO.getId());
            try {
                saveOrganizationLink(user, userRO.getOrgIdList(), userRO.getIsForceMerge() == 1);
                user.setCode(userRO.getNumber());
                user.setName(userRO.getName());
                user.setPhone(userRO.getPhone());
                user.setEmail(userRO.getEmail());
                user.setOrgId(userRO.getOrgId());
                user.setOrgName(userRO.getOrgName());
                user.setOrgCode(userRO.getOrgCode());
//                user.setBlueToothAddress(userRO.getBlueToothAddress());
                if(StringUtil.stringNotNull(userRO.getUserName())){
                    user.setUserName(userRO.getUserName());
                }
                if (StringUtil.stringNotNull(userRO.getTitle())) {
                    user.setTitle(userRO.getTitle());
                }
                this.update(user);
//                this.updateKrpanoUser(userRO.getPhone(), userRO.getName(), user.getId());

                msg = "修改成功";
            } catch (org.hibernate.exception.ConstraintViolationException e) {
                throw new RuntimeException("该用户在原所属机构中存在授权角色，是否强制合并已选择的所属机构？");
            }
        } else {
            throw new RuntimeException("未查询到要更新的用户");
        }
        return msg;
    }

    @Override
    public List<Role> getRolesByUserAndOrg(String userId, String organizationId) throws Exception {
        UserOrganization uerOrg = userOrganizationDao.findByUserIdAndOrganizationId(userId, organizationId);
        if (BaseUtil.objectNotNull(uerOrg)) {
            return StreamUtil.extractFieldToList(uerOrg.getUserOrganizationRoleList(), UserOrganizationRole::getRole);
        }
        return new ArrayList<>(0);
    }

    @Override
    public void saveOrganizationLink(User user, List<String> orgIdList, boolean isForceMerge) throws Exception {
        List<UserOrganization> userOrgList = userOrganizationDao.findByUserId(user.getId());
        List<UserOrganization> needSaveUserOrgList = BaseUtil.collectionNotNull(orgIdList) ? new ArrayList<>() : null;
        if (BaseUtil.collectionNotNull(orgIdList)) {
            Map<String, UserOrganization> userOrgMap = StreamUtil.convertCollectionToMap(userOrgList, uOrg -> uOrg.getOrganization().getId());
            for (String orgId : orgIdList) {
                UserOrganization userOrg = userOrgMap.get(orgId);
                userOrg = userOrg == null ? new UserOrganization() : userOrg;
                userOrg.setUser(user).setOrganization(ReflectUtil.setDomainId(new Organization(), orgId));
                needSaveUserOrgList.add(userOrg);
            }
        }
        if (isForceMerge) {
            userOrganizationDao.deleteAll(userOrgList);
            userOrganizationDao.flush();
            needSaveUserOrgList.forEach(userOrg -> userOrg.setId(null));
            userOrganizationDao.saveAll(needSaveUserOrgList);
        } else {
            this.mergeEntryList(userOrgList, needSaveUserOrgList);
        }
    }


    @Override
    public UserVO getById(String id) throws Exception {
        UserVO userVO = new UserVO(this.queryById(User.class, id));
        List<Organization> orgList = StreamUtil.extractFieldToList(userOrganizationDao.findByUserId(id), UserOrganization::getOrganization);
        userVO.setOrganizationVOList(VOUtil.convertDomainListToTempList(orgList, OrganizationVO.class));
        return userVO;
    }

    @Override
    public PageResult<UserVO> listPage(List<SearchCondition> searchConditionList,PageRO pageRO) throws Exception {
        PageCriteria pc = new PageCriteria(pageRO.getPage(), pageRO.getRows());
        PageResult<UserVO> userPageResult;
//        if (UserContextUtil.getAdmin() != null) {
            userPageResult = this.listPage(User.class, UserVO.class, pc, searchConditionList);
            List<UserVO> userVOList = userPageResult.getRows();
            if (BaseUtil.collectionNotNull(userVOList)) {
                List<UserOrganization> userOrgList = userOrganizationDao.findByUserIdIn(StreamUtil.extractFieldToList(userVOList, UserVO::getId));
                if (BaseUtil.collectionNotNull(userOrgList)) {
                    Map<String, UserVO> userVOMap = StreamUtil.convertCollectionToMap(userVOList, UserVO::getId);
                    for (UserOrganization userOrg : userOrgList) {
                        UserVO userVO = userVOMap.get(userOrg.getUser().getId());
                        List<OrganizationVO> orgVOList = userVO.getOrganizationVOList();
                        orgVOList = !BaseUtil.collectionNotNull(orgVOList) ? new ArrayList<>() : orgVOList;
                        orgVOList.add(new OrganizationVO(userOrg.getOrganization()));
                        userVO.setOrganizationVOList(orgVOList);
                    }
                }
            }
        return userPageResult;
    }

    @Override
    public List<UserVO> listAll(SearchConditionListRO searchConditionListRO) throws Exception {
        String orgCode = UserManager.getCurrentOrgCode();
        List<Organization> allSubOrg = SpringUtil.getBean(OrganizationServiceImpl.class).getAllSubOrg(orgCode);
        List<String> orgIds = StreamUtil.extractFieldToList(allSubOrg, Organization::getId);
//        DetachedCriteria criteria = DetachedCriteria.forClass(UserOrganization.class); // 每个用户可以属于多个机构
//        if (BaseUtil.collectionNotNull(orgIds))
//            criteria.add(Restrictions.in("organization.id", orgIds));
//        List<UserOrganization> userOrganizationList = list(criteria);
//        List<User> userList = StreamUtil.extractFieldToList(userOrganizationList, UserOrganization::getUser);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        if (BaseUtil.collectionNotNull(orgIds))
            detachedCriteria.add(Restrictions.in("orgId",orgIds));
//        List userList = list(detachedCriteria);
        return list(detachedCriteria,UserVO.class);
    }

}
