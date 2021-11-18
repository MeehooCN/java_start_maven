package com.meehoo.biz.core.basic.service.common;


import com.meehoo.biz.core.basic.domain.parent.AddressEntity;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.ro.bos.AddressEntityRO;

/**
 * 公共服务接口
 * Created by xg on 2016-11-17.
 */
public interface ICommonService {
    String getBizObjectSerialNumber(String bizObjName) throws Exception;

    User getCurrentUser(String currUserId) throws Exception;

    AddressEntity setProvinceCityAreaAndAddress(AddressEntity addressEntity, AddressEntityRO addressEntityRO);
}
