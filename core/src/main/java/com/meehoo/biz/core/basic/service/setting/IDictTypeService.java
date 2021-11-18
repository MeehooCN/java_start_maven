package com.meehoo.biz.core.basic.service.setting;



import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.setting.DictTypeVO;

import java.util.List;

/**
 * 数据字典 类型
 * Created by xg on 2017/5/11.
 */
public interface IDictTypeService extends IBaseService {

    /**
     * @param pageCriteria
     * @param searchContent
     * @return 列表
     * @throws Exception
     */
    PageResult<DictTypeVO> list(PageCriteria pageCriteria, String searchContent) throws Exception;

    List<DictTypeVO> getAllDictType() throws Exception;
}
