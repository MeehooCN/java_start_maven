package com.meehoo.biz.core.basic.dao.bos;

import com.meehoo.biz.core.basic.domain.bos.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by CZ on 2018/7/27.
 */
public interface IMenuDao extends JpaRepository<Menu, String> {

    @Query("from Menu e where e.menuType = ?1")
    List<Menu> queryByMenuType(Integer menuType)throws Exception;

    @Query("select coalesce(max(e.seq),0) from Menu e where e.grade = 0")
    Integer getRootGradeMaxSeq();

    @Query("select coalesce(max(e.seq),0) from Menu e where e.grade = ?1 and e.parentMenu.id = ?2")
    Integer getMaxSeqByGradeAndParentMenu(Integer grate,String parentMenuId);

    @Query("from Menu e where e.code like ?1")
    List<Menu> queryByCodeLike(String code)throws Exception;
}
