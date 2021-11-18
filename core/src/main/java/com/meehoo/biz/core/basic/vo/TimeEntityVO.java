package com.meehoo.biz.core.basic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meehoo.biz.core.basic.domain.BaseEntity;
import com.meehoo.biz.core.basic.domain.TimeEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zc
 * @date 2021-06-04
 */
@Setter
@Getter
public class TimeEntityVO extends BaseEntityVO{
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    public TimeEntityVO(TimeEntity timeEntity) {
        super(timeEntity);
        this.updateTime = timeEntity.getUpdateTime();
        this.createTime = timeEntity.getCreateTime();
    }
}
