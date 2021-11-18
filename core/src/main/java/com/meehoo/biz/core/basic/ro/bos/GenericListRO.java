package com.meehoo.biz.core.basic.ro.bos;

import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用接收数组的RO,不需要再为接收某组RO而单独新建一个List&lt;RO&gt;的包装类<br/>
 * example:<br/>
 * <p>
 * {@link IdListRO}可替换为 GenericListRO&lt;String&gt; <br/>
 * {@link SearchConditionListRO} 可替换为GenericListRO&lt;SearchCondition&gt;
 * </p>
 * 继承{@link IdRO}，用于一对多更新的场景
 * Created by CZ on 2018/11/23.
 */
@Setter
public class GenericListRO<T> extends IdRO {
    private List<T> roList;

    public List<T> getRoList() {
        return roList==null?new ArrayList<>(0):roList;
    }
}
