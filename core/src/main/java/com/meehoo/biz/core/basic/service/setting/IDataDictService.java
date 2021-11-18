package com.meehoo.biz.core.basic.service.setting;



import com.meehoo.biz.core.basic.domain.setting.DataDict;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.service.IBaseService;

import java.util.List;

/**
 * 数据字典service
 * @author lixiaobin
 */
public interface IDataDictService extends IBaseService {

    /**
     * 根据预先定义类型dType查询一个类型中的全部对象
     * @param dType
     * @return
     * @throws Exception
     * added by wrc
     */
    List<DataDict> queryByType(Integer dType)throws Exception;

    PageResult<DataDict> list(PageCriteria pageCriteria, String type, String code, String value, String introduction)throws Exception;
}
