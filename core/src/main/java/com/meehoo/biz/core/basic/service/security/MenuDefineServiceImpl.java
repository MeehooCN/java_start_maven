package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.bos.Menu;
import com.meehoo.biz.core.basic.domain.security.MenuDefine;
import com.meehoo.biz.core.basic.domain.security.Role2MenuDefine;
import com.meehoo.biz.core.basic.service.common.BaseServicePlus;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.bos.AuthDefineVO;
import com.meehoo.biz.core.basic.vo.bos.MenuWithDefineVO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zc
 * @date 2020-12-23
 */
@Service
@Transactional
public class MenuDefineServiceImpl extends BaseServicePlus implements IMenuDefineService {

    @Override
    public AuthDefineVO getMenuWithDefines(String roleId) {
        AuthDefineVO authDefineVO = new AuthDefineVO();
        // 有自定义权限的页面
        DetachedCriteria dc = DetachedCriteria.forClass(MenuDefine.class);
        dc.add(Restrictions.eq("isDelete",0));
        List<MenuDefine> list = list(dc);

        Set<Menu> collect = list.stream().map(e -> e.getMenu()).collect(Collectors.toSet());
        List<MenuWithDefineVO> menuWithDefineVOS = VOUtil.convertDomainListToTempList(new ArrayList<>(collect), MenuWithDefineVO.class);
        authDefineVO.setDefineVOList(menuWithDefineVOS);

        // 角色已有的自定义权限
        DetachedCriteria roleDc = DetachedCriteria.forClass(Role2MenuDefine.class);
        roleDc.add(Restrictions.eq("role.id",roleId));
        List<Role2MenuDefine> roleList = list(roleDc);

        List<String> defineIds = roleList.stream().map(e -> e.getMenuDefine().getId()).collect(Collectors.toList());
        authDefineVO.setAuthList(defineIds);
        return authDefineVO;
    }

    @Override
    public AuthDefineVO getDefineByMenu(String menuId, String roleId) {
        AuthDefineVO authDefineVO = new AuthDefineVO();
        Menu menu = queryById(Menu.class, menuId);
        List<MenuWithDefineVO> defineVOS = new ArrayList<>();
        defineVOS.add(new MenuWithDefineVO(menu));
        authDefineVO.setDefineVOList(defineVOS);

        DetachedCriteria roleDc = DetachedCriteria.forClass(Role2MenuDefine.class);
        roleDc.add(Restrictions.eq("role.id",roleId));
        roleDc.createAlias("menuDefine","menuDefine");
        roleDc.add(Restrictions.eq("menuDefine.menu.id",menuId));
        List<Role2MenuDefine> list = list(roleDc);
        List<String> collect = list.stream().map(e -> e.getMenuDefine().getId()).collect(Collectors.toList());
        authDefineVO.setAuthList(collect);

        return authDefineVO;
    }

    @Override
    public boolean hasAuthDefine(String roleId,String defineId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Role2MenuDefine.class);
        dc.add(Restrictions.eq("role.id",roleId));
        dc.add(Restrictions.eq("menuDefine.id",defineId));
        List list = list(dc);
        return BaseUtil.listNotNull(list);
    }
}
