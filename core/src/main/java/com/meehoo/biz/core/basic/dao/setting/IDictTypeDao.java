package com.meehoo.biz.core.basic.dao.setting;

import com.meehoo.biz.core.basic.domain.setting.DictType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xg
 * @create 2017-05-11-13:28
 */
public interface IDictTypeDao extends JpaRepository<DictType, String> {
    @Query("FROM DictType d ")
    List<DictType> getAllDictType();

    @Query("FROM DictType d where d.module = ?1 ")
    List<DictType> getAllDictTypeByModule(String module);
}
