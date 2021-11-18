package com.meehoo.biz.core.basic.hierarchy;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.List;

/**
 * 层级结构
 * @author zc
 * @date 2020-04-13
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@Accessors(chain = true)
public class HierarchyDomain extends IdEntity {
    @Column
    private String code;

    @Column(columnDefinition = "int default 0")
    private int isDelete;

    @Column(columnDefinition = "int default 0")
    private int childrenQty; // (包括删除子对象)

    @Transient
    private List<HierarchyDomain> children;

    @Column(columnDefinition = "int default 1")
    private int isLeaf;

    public String getInsertChildCode() {
        return code+ StringUtil.numberToCode(childrenQty+1,3);
    }

    public String getParentCode(){
        if (code.length()>3)
            return code.substring(0,code.length()-3);
        return "";
    }

    public int getLevel(){
        return code.length()/3;
    }
}
