package com.meehoo.biz.core.basic.dao.setting;


import com.meehoo.biz.core.basic.domain.setting.DataDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据字典dao
 * @author lixiaobin
 */
public interface IDataDictDao extends JpaRepository<DataDict, Long> {

    /**
     * 预先定义类型查询
     * @param dType
     * @return
     * @throws Exception
     */
    @Query("select dict FROM DataDict dict where dict.dType = ?1")
    public List<DataDict> queryByType(Integer dType) throws Exception;
}
