package com.meehoo.biz.core.basic.ro.bos;

import lombok.Data;

import java.util.List;

/**
 * 中间表设置
 * @author zc
 * @date 2019-09-03
 */
@Data
public class BatchUpdateRO {
    private String mainId;

    private List<String> subIds;
}
