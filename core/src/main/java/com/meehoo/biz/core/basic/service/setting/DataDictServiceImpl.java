package com.meehoo.biz.core.basic.service.setting;


import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.setting.IDataDictDao;
import com.meehoo.biz.core.basic.domain.setting.DataDict;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.service.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典service接口实现
 * @author lixiaobin
 */
@Service
@Transactional
public class DataDictServiceImpl extends BaseService implements IDataDictService{

    private final IDataDictDao dictDao;

    @Autowired
    public DataDictServiceImpl(IDataDictDao dictDao) {
        this.dictDao = dictDao;
    }

    /**
     * 根据预先定义类型dType查询一个类型中的全部对象
     * @param dType
     * @return
     * @throws Exception
     * added by wrc
     */
    public List<DataDict> queryByType(Integer dType)throws Exception{
        return dictDao.queryByType(dType);
    }

    @Override
    public PageResult<DataDict> list(PageCriteria pageCriteria, String type, String code, String value, String introduction)throws Exception{
        DetachedCriteria dc = DetachedCriteria.forClass(DataDict.class);

        if(StringUtil.stringNotNull(type)){
            dc.add(Restrictions.eq("dType",Integer.valueOf(type)));
        }
        if (StringUtil.stringNotNull(code)){
            dc.add(Restrictions.like("dCode", code, MatchMode.ANYWHERE));
        }
        if (StringUtil.stringNotNull(value)){
            dc.add(Restrictions.like("dValue", value,MatchMode.ANYWHERE));
        }
        if (StringUtil.stringNotNull(introduction)){
            dc.add(Restrictions.like("introduction", introduction,MatchMode.ANYWHERE));
        }

        dc.addOrder(Order.asc("dType"));

        return page(dc, pageCriteria);
    }
}
