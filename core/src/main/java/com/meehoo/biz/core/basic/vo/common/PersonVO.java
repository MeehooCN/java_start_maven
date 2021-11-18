package com.meehoo.biz.core.basic.vo.common;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.BigDecimalUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.core.basic.domain.parent.PersonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zc
 * @date 2019-07-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PersonVO extends AddressEntityVO{
    private String phoneNum;

    private String weiChat; // 微信号

//    private String address;

    private int sex; // 性别    0 女    1 男

    //    private int age; // 年龄
    private Date birthDay;

    private String birthDayShow;

    public PersonVO(PersonEntity personEntity) {
        super(personEntity);
        this.phoneNum = personEntity.getPhoneNum();
        this.weiChat = personEntity.getWeiChat();
//        this.address = personEntity.getAddress();
        this.sex = personEntity.getSex();
        this.birthDay = personEntity.getBirthDay();
        this.birthDayShow = DateUtil.timeToString(birthDay);
//        this.age = personEntity.getAge();
    }

    public String getAge(){
        if (birthDay!=null){
            double l = new Date().getTime() - birthDay.getTime();
            double i = (long)(1000 * 60 * 60) * (long)(24 * 365);
            return (int) BigDecimalUtil.div(l,i)+"";
        }else{
            return "";
        }
    }
}
