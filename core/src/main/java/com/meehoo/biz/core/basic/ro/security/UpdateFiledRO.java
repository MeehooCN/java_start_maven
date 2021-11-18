package com.meehoo.biz.core.basic.ro.security;

import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zc
 * @date 2020-04-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UpdateFiledRO extends IdRO {
    private Map<String,Object> params;// ,key是字段名，value是修改后的值

    public UpdateFiledRO(String id, Map<String, Object> params) {
        this.id = id;
        this.params = params;
    }
}
