package com.meehoo.biz.core.basic.dao.setting;

import com.meehoo.biz.core.basic.domain.setting.SystemParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016-12-13.
 */
public interface ISystemParamDao extends JpaRepository<SystemParam, String> {
    @Query("FROM SystemParam p WHERE p.code=?1")
    List<SystemParam> findByNumber(String number);
}
