package com.meehoo.biz.core.basic.ro.bos;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zc
 * @date 2019-07-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PersonRO extends AddressEntityRO {
    private String phoneNum;

    private String weiChat; // 微信号

    private String address;

    private int sex; // 性别    0 女    1 男

//    private int age; // 年龄

    private String birthDay; //生日
}
