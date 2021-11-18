package com.meehoo.biz.core.basic.service.setting;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.setting.IDictValueDao;
import com.meehoo.biz.core.basic.domain.setting.DictValue;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.vo.setting.DictValueVO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典的值
 *
 * @author xg
 * @create 2017-05-11-14:18
 */
@Service
@Transactional
public class DictValueServiceImpl extends BaseService implements IDictValueService {
    private IDictValueDao dictValueDao;

    @Autowired
    public DictValueServiceImpl(IDictValueDao dictValueDao) {
        this.dictValueDao = dictValueDao;
    }

    /**
     * @param typeId     type的id
     * @param typeNumber type的number
     * @return
     * @throws Exception
     */
    @Override
    public PageResult<DictValueVO> list(PageCriteria pageCriteria,
                                        String typeId, String typeNumber)
            throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(DictValue.class);
        dc.createAlias("dictType", "dictType");
        if (StringUtil.stringNotNull(typeNumber)) {
            dc.add(Restrictions.eq("dictType.number", typeNumber));
        }
        if (StringUtil.stringNotNull(typeId)) {
            dc.add(Restrictions.eq("dictType.id", typeId));
        }
        dc.addOrder(Order.asc("mkey"));
        return page(dc, pageCriteria, DictValueVO.class);
    }

    @Override
    public List<DictValueVO> getByTypeModuleNumber(String typeModule, String typeNumber) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(DictValue.class);
        if(StringUtil.stringNotNull(typeModule) || StringUtil.stringNotNull(typeNumber)){
            dc.createAlias("dictType", "dictType");
            if (StringUtil.stringNotNull(typeModule)) {
                dc.add(Restrictions.eq("dictType.module", typeModule));
            }
            if (StringUtil.stringNotNull(typeNumber)) {
                dc.add(Restrictions.eq("dictType.code", typeNumber));
            }
        }

        if("system".equals(typeModule)) {
            dc.addOrder(Order.asc("id"));
        }else{
            dc.addOrder(Order.asc("mkey"));
        }
        return  this.list(dc,DictValueVO.class);
    }

    @Override
    public List<DictValueVO> getByTypeNumber(String typeNumber) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(DictValue.class);
        if (StringUtil.stringNotNull(typeNumber)) {
            dc.createAlias("dictType", "dictType");
            dc.add(Restrictions.eq("dictType.code", typeNumber));
        }
        dc.addOrder(Order.asc("mkey"));
        return  this.list(dc,DictValueVO.class);
    }

    @Override
    public List<DictValueVO> getByTypeId(String typeId) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(DictValue.class);
        if (StringUtil.stringNotNull(typeId)) {
            dc.createAlias("dictType", "dictType");
            dc.add(Restrictions.eq("dictType.id", typeId));
        }
        dc.addOrder(Order.asc("mkey"));
        return  this.list(dc,DictValueVO.class);
    }

    @Override
    public List<DictValueVO> getSysSetByTypeNumber(String typeNumber) throws Exception{
        DetachedCriteria dc = DetachedCriteria.forClass(DictValue.class);
        if (StringUtil.stringNotNull(typeNumber)) {
            dc.createAlias("dictType", "dictType");
            dc.add(Restrictions.eq("dictType.code", typeNumber));
            dc.add(Restrictions.eq("isSysSet",DictValue.ISSYSSET_YES));
        }
        dc.addOrder(Order.asc("mkey"));
        return  this.list(dc,DictValueVO.class);
    }

    @Override
    public DictValue getByTypeNumberAndKey(String typeNumber, String key) {
        return dictValueDao.getByMkeyAndDictType_Code(key,typeNumber);
    }
}
