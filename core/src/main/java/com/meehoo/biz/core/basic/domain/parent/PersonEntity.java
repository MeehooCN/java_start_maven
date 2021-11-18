package com.meehoo.biz.core.basic.domain.parent;

import com.meehoo.biz.common.util.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author zc
 * @date 2019-07-15
 */
@MappedSuperclass
@Accessors(chain = true)
@Data
public class PersonEntity extends AddressEntity{
    /**
     * 用户姓名
     */
    @Column(length = 80)
    private String name;

    @Column(length = 50)
    private String phoneNum;

    @Column(length = 100)
    private String weiChat; // 微信号

    @Column(columnDefinition = "int default 0")
    private int sex; // 性别    0 女    1 男

//    @Column(columnDefinition ="int default 0")
//    private int age; // 年龄

    @Column
    private Date birthDay;

    public int getAge(){
        return DateUtil.countYear(birthDay);
    }
}
