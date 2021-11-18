package com.meehoo.biz.core.basic.service.setting;


import com.meehoo.biz.core.basic.domain.setting.SystemParam;
import com.meehoo.biz.core.basic.service.IBaseService;

/**
 * Created by Administrator on 2016-12-13.
 */
public interface ISystemParamService extends IBaseService {
    SystemParam getSystemParamByNumber(String number) throws Exception;
}
