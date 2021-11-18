package com.meehoo.biz.core.basic.service.setting;



import com.meehoo.biz.core.basic.domain.setting.DictValue;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.setting.DictValueVO;

import java.util.List;

/**
 * 数据字典 值
 *
 * @author ZK
 * @create 2017-05-11-14:07
 */
public interface IDictValueService extends IBaseService {
    /**
     * @param pageCriteria
     * @return 列表
     * @throws Exception
     */
    PageResult<DictValueVO> list(PageCriteria pageCriteria, String
            typeId, String typeNumber) throws Exception;

    /**
     * 根据数据字典类型Key获取数据
     */
    List<DictValueVO>  getByTypeModuleNumber(String typeModule, String typeNumber) throws Exception;

    /**
     * 根据数据字典类型Key获取数据
     */
    List<DictValueVO>  getByTypeNumber(String typeNumber) throws Exception;

    /**
     * 根据数据字典类型Key获取数据
     */
    List<DictValueVO>  getByTypeId(String typeId) throws Exception;

    /**
     * 根据数据字典类型Key获取系统初始数据
     */
    List<DictValueVO> getSysSetByTypeNumber(String typeNumber) throws Exception;

    DictValue getByTypeNumberAndKey(String typeNumber, String key);
}
