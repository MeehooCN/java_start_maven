package com.meehoo.biz.core.basic.dao.setting;

import com.meehoo.biz.core.basic.domain.setting.DictValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by xg on 2017/5/11.
 */
public interface IDictValueDao extends JpaRepository<DictValue, String> {
    @Query("from DictValue dv where dv.mkey = ?1")
    List<DictValue> getByKey(String key) throws Exception;

    DictValue getByMkeyAndDictType_Code(String key, String typeNumber);

    @Query("from DictValue dv where dv.mkey = ?1")
    DictValue getByMkey(String key) throws Exception;
}
