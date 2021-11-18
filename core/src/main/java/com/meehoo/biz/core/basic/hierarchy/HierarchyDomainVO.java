package com.meehoo.biz.core.basic.hierarchy;

import com.meehoo.biz.core.basic.util.ReflectUtil;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zc
 * @date 2020-04-14
 */
@Getter
@Setter
public class HierarchyDomainVO<T extends HierarchyDomainVO> extends IdEntityVO {
    private String code;
    private int isDelete;
    private int childrenQty; // (包括删除子对象)
    private List<T> children;
    private int isLeaf;

    public HierarchyDomainVO(HierarchyDomain hierarchyDomain) throws Exception{
        super(hierarchyDomain);
        this.code = hierarchyDomain.getCode();
        this.isDelete = hierarchyDomain.getIsDelete();
        childrenQty = hierarchyDomain.getChildrenQty();
        isLeaf = hierarchyDomain.getIsLeaf();
        Class<?>[] classes = ReflectUtil.getActualTypes(getClass().getGenericSuperclass());
        if (classes!=null)
            children = VOUtil.convertDomainListToTempList(hierarchyDomain.getChildren(),(Class<T>) classes[0]);
    }
}
