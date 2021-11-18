package com.meehoo.biz.core.basic.ro;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zc
 * @date 2021-06-04
 */
@Setter
@Getter
public class TimeEntityRO extends BaseEntityRO{
    private String updateTime;

    private String createTime;
}
