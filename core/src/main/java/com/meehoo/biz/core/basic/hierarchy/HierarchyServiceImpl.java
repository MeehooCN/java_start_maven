package com.meehoo.biz.core.basic.hierarchy;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.service.common.BaseServicePlus;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zc
 * @date 2020-04-14
 */
@Component
public class HierarchyServiceImpl extends BaseServicePlus {
    public <T extends HierarchyDomain> T createEntity(Class<T> tClass, HierarchyDomainRO ro)throws Exception{
        T t = tClass.newInstance();
        String code;
        if (StringUtil.stringNotNull(ro.getParentId())){
            T parent = queryById(tClass, ro.getParentId());
            code = parent.getInsertChildCode();
            parent.setChildrenQty(parent.getChildrenQty()+1);
            if (parent.getIsLeaf()==1){
                parent.setIsLeaf(0);
            }
            update(parent);
        }else{
            int size = getRoot(tClass).size();
            code = StringUtil.numberToCode(size+1,3);
        }
        t.setCode(code)
                .setIsLeaf(1);
        return t;
    }

    public <T extends HierarchyDomain> List<T> getRoot(Class<T> tClass)throws Exception{
        DetachedCriteria dc = DetachedCriteria.forClass(tClass);
        dc.add(Restrictions.sqlRestriction("LENGTH(code)=3"));
        return list(dc);
    }

    public <T extends HierarchyDomain> List<T> getChildren(Class<T> tClass, String parentCode, int showDelete)throws Exception{
        DetachedCriteria dc = DetachedCriteria.forClass(tClass);
        if(StringUtil.stringNotNull(parentCode)){
            dc.add(Restrictions.like("code",parentCode, MatchMode.START));
        }
        if (showDelete==0){
            dc.add(Restrictions.eq("isDelete",0));
        }
        dc.addOrder(Order.asc("code"));
        return list(dc);
    }

    public <T extends HierarchyDomain> List<T> getChildrenInTree(Class<T> tClass, String parentCode, int showDelete)throws Exception{
        List<T> list = getChildren(tClass,parentCode, showDelete);
        return convertIntoTree(list,parentCode.length()+3);
    }

    public  <T extends HierarchyDomain> List<T> convertIntoTree(List<T> srt, int rootCodeLength){
        List<T> dst = new ArrayList<>();
        for (T t : srt) {
            String code = t.getCode();
            if (code.length()==rootCodeLength){
                dst.add(t);
            }else{
                for (T t1 : srt) {
                    String code1 = t1.getCode();
                    if (code.startsWith(code1)&&code.length()==code1.length()+3){
                        if (t1.getChildren()==null){
                            t1.setChildren(new ArrayList<>());
                        }
                        t1.getChildren().add(t);
                        break;
                    }
                }
            }
        }
        return dst;
    }

    public <T extends HierarchyDomain> T getParent(T t)throws Exception{
        String parentCode = t.getParentCode();
        return queryByColumn(t.getClass(),"code",parentCode);
    }
}
