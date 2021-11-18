package com.meehoo.biz.core.basic.service.common;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.bos.SerialNumber;
import com.meehoo.biz.core.basic.domain.parent.AddressEntity;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.ro.bos.AddressEntityRO;
import com.meehoo.biz.core.basic.service.bos.ISerialNumberService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Administrator on 2016-11-17.
 */
@Service
public class CommonServiceImpl implements ICommonService {
    private ISerialNumberService serialNumberService;

    @Autowired
    public CommonServiceImpl(ISerialNumberService serialNumberService) {
        this.serialNumberService = serialNumberService;
    }

    @Override
    @Transactional
    public String getBizObjectSerialNumber(String bizObjName) throws Exception {
        String       strRet          = "";
        SerialNumber oldSerialNumber = null;

        Map<String, Object> mapSerialNumber = serialNumberService.getBizObjectMaxSeq(bizObjName);
        if (BaseUtil.objectNotNull(mapSerialNumber) && mapSerialNumber.size() > 0) {
            String strNewSerialNumber = mapSerialNumber.get("newSerialNumber").toString();
            if (!strNewSerialNumber.equals("error")) {
                strRet = strNewSerialNumber;
                oldSerialNumber = (SerialNumber) mapSerialNumber.get("oldSerialNumber");
            }
        }

        if (BaseUtil.objectNotNull(oldSerialNumber)) {
            oldSerialNumber.setSeq(oldSerialNumber.getSeq() + 1);
            serialNumberService.update(oldSerialNumber);
        }

        return strRet;
    }

    @Override
    public User getCurrentUser(@NonNull String currUserId) throws Exception {
        User currUser = serialNumberService.queryById(User.class, currUserId);
        if (!BaseUtil.objectNotNull(currUser)) {
            throw new RuntimeException("id为:" + currUserId + "的用户信息不存在");
        }
        return currUser;
    }

    @Override
    public AddressEntity setProvinceCityAreaAndAddress(AddressEntity addressEntity, AddressEntityRO addressEntityRO) {
        if (addressEntity != null && addressEntityRO != null) {
//            List<String> provinceCityAreaList = addressEntityRO.getProvinceCityArea();
//            if (BaseUtil.listNotNull(provinceCityAreaList)) {
            //获取省
//                String[] strPr0vince = provinceCityAreaList.get(0).split("\\|");
            addressEntity.setProvinceCode(addressEntityRO.getProvinceCode());
            addressEntity.setProvince(addressEntityRO.getProvince());
            //获取市
//                String[] strCity = provinceCityAreaList.get(1).split("\\|");
            addressEntity.setCityCode(addressEntityRO.getCityCode());
            addressEntity.setCity(addressEntityRO.getCity());
            //获取区
//                String[] strArea = provinceCityAreaList.get(2).split("\\|");
            addressEntity.setAreaCode(addressEntityRO.getAreaCode());
            addressEntity.setArea(addressEntityRO.getArea());
//            }
            addressEntity.setAddress(addressEntityRO.getAddress());
        }
        return addressEntity;
    }
}
