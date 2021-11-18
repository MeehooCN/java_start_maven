package com.meehoo.biz.core.basic.service.setting;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.security.IUserOrganizationDao;
import com.meehoo.biz.core.basic.dao.setting.IOptLogDao;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.domain.setting.OptLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wangjian on 2017-12-05.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OptLogServiceImpl implements IOptLogService{

    private final IUserOrganizationDao userOrganizationDao;

    private final IOptLogDao optLogDao;

    @Autowired
    public OptLogServiceImpl(IUserOrganizationDao userOrganizationDao, IOptLogDao optLogDao) {
        this.userOrganizationDao = userOrganizationDao;
        this.optLogDao = optLogDao;
    }

    @Override
    public void saveLog(User admin, String opt, Object...optParams)throws Exception{
        if(admin!=null&& StringUtil.stringNotNull(opt)){
            if(optParams!=null&&optParams.length>0){
                opt = String.format(opt,optParams);
            }
            OptLog optLog = new OptLog();
            optLog.setUserId(admin.getId());
            optLog.setUserName(admin.getName());
            optLog.setOpt(opt);
            optLog.setCreateTime(new Date());
//            UserOrganization usrOrg = userOrganizationDao.findByUserIdAndOrganizationId(admin.getId(), admin.getUserContextInfo().getCurrOrgId());
//            if(usrOrg!=null){
//                admin = usrOrg.getUser();
//                Organization organization =usrOrg.getOrganization();
//                optLog.setUserName(admin.getName());
//                optLog.setOrgId(organization.getId());
//                optLog.setOrgName(organization.getName());
////                optLog.setProOrgType(organization.getProOrgType());
//                List<UserOrganizationRole> userOrgRoleList = usrOrg.getUserOrganizationRoleList();
//                if(BaseUtil.collectionNotNull(userOrgRoleList)){
//                    StringBuilder roleNameBuilder = new StringBuilder();
//                    Iterator<UserOrganizationRole> iterator = userOrgRoleList.iterator();
//                    while (iterator.hasNext()){
//                        roleNameBuilder.append(iterator.next().getRole().getName());
//                        if(iterator.hasNext()){
//                            roleNameBuilder.append(',');
//                        }
//                    }
//                    optLog.setRoleName(roleNameBuilder.toString());
//                }
//            }
//            optLogDao.save(optLog);
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if(BaseUtil.collectionNotNull(idList)){
            int listSize = idList.size();
            if(listSize<50){
                optLogDao.deleteAll(optLogDao.findAllById(idList));
            }else {
                int n = listSize/50;
                int remain = listSize%50;
                for (int i = 0; i < n ; i++) {
                    optLogDao.deleteAll(optLogDao.findAllById(idList.subList(i*50,(i+1)*50)));
                    optLogDao.flush();
                }
                if(remain>0){
                    optLogDao.deleteAll(optLogDao.findAllById(idList.subList(n*50,n*50+remain)));
                }
            }
        }
    }

}
