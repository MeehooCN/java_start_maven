package com.meehoo.biz.core.basic.service.setting;


import com.meehoo.biz.core.basic.domain.setting.ProvinceCityArea;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.setting.ProvinceCityAreaSelectVO;

import java.util.List;

/**
 * Created by Administrator on 2016-10-19.
 */
public interface IProvinceCityAreaService extends IBaseService {
    /**
     * 按省份名称获取省市区
     * @param provinceName
     * @return
     * @throws Exception
     */
    ProvinceCityAreaSelectVO getProvinceCityAreaSelectVO(String provinceName) throws Exception;
    //返回的value格式不带name
    ProvinceCityAreaSelectVO getProvinceCityAreaSelectVOForApi(String provinceName) throws Exception;
    /**
     * 获取所有省市区
     * @param
     * @return
     * @throws Exception
     */
    ProvinceCityAreaSelectVO getAllProvinceCityAreaSelectVO(String except) throws Exception;
    //返回的value格式不带name
    ProvinceCityAreaSelectVO getAllProvinceCityAreaSelectVOForApi(String except) throws Exception;

    ProvinceCityAreaSelectVO getOnlyProvince()throws Exception;

    ProvinceCityAreaSelectVO getOnlyCity(String code)throws Exception;

    ProvinceCityAreaSelectVO getOnlyArea(String provinceCode, String cityCode)throws Exception;

    List<ProvinceCityArea> getArea(String provinceCode, String cityCode)throws Exception;
}
