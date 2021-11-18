package com.meehoo.biz.core.basic.service.setting;


import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.dao.setting.ISystemParamDao;
import com.meehoo.biz.core.basic.domain.setting.SystemParam;
import com.meehoo.biz.core.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016-12-13.
 */
@Service
@Transactional
public class SystemParamServiceImpl extends BaseService implements ISystemParamService {
    private final ISystemParamDao systemParamDao;

    @Autowired
    public SystemParamServiceImpl(ISystemParamDao systemParamDao) {
        this.systemParamDao = systemParamDao;
    }

    @Override
    public SystemParam getSystemParamByNumber(String number) throws Exception {
        List<SystemParam> systemParamList = systemParamDao.findByNumber(number);
        if(BaseUtil.collectionNotNull(systemParamList)){
            return systemParamList.get(0);
        }

        return null;
    }
}
