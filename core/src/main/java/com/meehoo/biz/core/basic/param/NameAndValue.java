package com.meehoo.biz.core.basic.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zc
 * @date 2020-07-10
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NameAndValue {
    private String name;

    private Object value;
}
