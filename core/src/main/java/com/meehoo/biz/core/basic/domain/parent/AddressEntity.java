package com.meehoo.biz.core.basic.domain.parent;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 位置信息
 * Created by CZ on 2018/1/22.
 */
@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
public class AddressEntity extends BaseEntity {
    /**
     * 省
     */
    @Column(length = 50)
    protected String provinceCode;

    @Column(length = 100)
    protected String province;

    /**
     * 市
     */
    @Column(length = 50)
    protected String cityCode;

    @Column(length = 100)
    protected String city;

    /**
     * 区
     */
    @Column(length = 50)
    protected String areaCode;

    @Column(length = 100)
    protected String area;

    /**
     * 详细地址
     */
    @Column
    protected String address;

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (StringUtil.stringNotNull(province)) {
            sb.append(province);
        }
        if (StringUtil.stringNotNull(city)) {
            if (!city.equals(sb.toString())) {
                sb.append(city);
            }
        }
        if (StringUtil.stringNotNull(area)) {
            sb.append(area);
        }
        if (StringUtil.stringNotNull(address)) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(address);
        }
        return sb.toString();
    }

}
