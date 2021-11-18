package com.meehoo.biz.core.basic.ro;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CZ on 2018/1/17.
 */
@Getter
@Setter
public class BaseEntityRO extends IdRO {

    protected String code;

    protected String name;

    protected int isDelete;

    protected int enable;
}