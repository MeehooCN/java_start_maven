package com.meehoo.biz.core.basic.dao.bos;

import com.meehoo.biz.core.basic.domain.bos.SerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by XG on 2016-06-01.
 */
public interface ISerialNumberDao extends JpaRepository<SerialNumber, Long> {
    @Query("From SerialNumber sn Where sn.bizObject =?1")
    List<SerialNumber> queryBizObjSerialNumber(String bizObject) throws Exception;
}
