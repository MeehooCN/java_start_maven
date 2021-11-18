package com.meehoo.biz.core.basic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by CZ on 2018/1/16.
 */
@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
public class IdEntity {
    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid") // 32位随机数字
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" ) // 32位随机数字+4个-
    protected String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdEntity)) return false;

        IdEntity idEntity = (IdEntity) o;

        return id != null ? id.equals(idEntity.id) : idEntity.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
