package com.meehoo.biz.core.basic.dao.setting;

import com.meehoo.biz.core.basic.domain.setting.OptLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by cz on 2018/11/30
 */
public interface IOptLogDao extends JpaRepository<OptLog,String> {

}