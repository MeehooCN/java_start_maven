package com.meehoo.biz.core.basic.ro.bos;

import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/11/03.
 */
@Getter
@Setter
public class ChangeStatusRO extends IdRO {
    private int enable;
}
