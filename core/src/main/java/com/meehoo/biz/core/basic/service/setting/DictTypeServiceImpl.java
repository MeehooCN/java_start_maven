package com.meehoo.biz.core.basic.service.setting;


import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.setting.IDictTypeDao;
import com.meehoo.biz.core.basic.domain.setting.DictType;
import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.setting.DictTypeVO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典 类型
 *
 * @author xg
 * @create 2017-05-11-13:55
 */
@Service
@Transactional
public class DictTypeServiceImpl extends BaseService implements IDictTypeService {
    private IDictTypeDao dictTypeDao;

    @Autowired
    public DictTypeServiceImpl(IDictTypeDao dictTypeDao) {
        this.dictTypeDao = dictTypeDao;
    }

    @Override
    public PageResult<DictTypeVO> list(PageCriteria pageCriteria, String searchContent) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(DictType.class);

        if (StringUtil.stringNotNull(searchContent)) {
            String likeContent = "%" + searchContent.trim() + "%";
            dc.add(Restrictions.or(Restrictions.like("name", likeContent), Restrictions.like("number", likeContent)));
        }
        dc.addOrder(Order.asc("number"));

        return page(dc, pageCriteria,DictTypeVO.class);
    }

    @Override
    public List<DictTypeVO> getAllDictType() throws Exception {
        return VOUtil.convertDomainListToTempList(dictTypeDao.getAllDictType(),DictTypeVO.class);
    }
}
