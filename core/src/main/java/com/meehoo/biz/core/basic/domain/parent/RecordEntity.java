package com.meehoo.biz.core.basic.domain.parent;

import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author zc
 * @date 2020-01-02
 */
@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
public abstract class RecordEntity extends BaseEntity {
    @Column(length = 1024)
    private String record;

    @Column(columnDefinition = "Int default 0")
    private int process;

    protected abstract String getProcessString(int process);

    public void exeRecord(String newRecord){
        newRecord = DateUtil.dateToString(new Date())+" "+newRecord+";";
        if (StringUtil.stringNotNull(record))
            record+=newRecord;
        else
            record = newRecord;
    }
}
