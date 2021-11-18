package com.meehoo.biz.core.basic.service.bos;

import com.meehoo.biz.common.util.StreamUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.bos.IMenuDao;
import com.meehoo.biz.core.basic.domain.bos.Menu;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.bos.MenuVO;
import com.meehoo.biz.core.basic.vo.setting.MenuRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Created by cz on 2018/07/27.
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseService implements IMenuService {

    private final IMenuDao menuDao;

    @Autowired
    public MenuServiceImpl(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public List<MenuVO> listAll() throws Exception {
        List<Menu> menuList = menuDao.findAll();
        List<Menu> topMenus = new ArrayList<>(5);
        for (Menu menu : menuList) {
            if (menu.getStatus() == Menu.STATUS_ENABLE) {
                if (menu.getGrade() == Menu.GRADE_ROOT) {
                    topMenus.add(menu);
                } else {
                    menu.getParentMenu().addChildMenu(menu);
                }
            }
        }
        topMenus.sort(Comparator.comparingInt(Menu::getSeq));
        return VOUtil.convertDomainListToTempList(topMenus, MenuVO.class);
    }

    @Override
    public Menu addMenu(MenuRO ro) throws Exception{
        Menu menu = new Menu();
        if(StringUtil.stringIsNull(ro.getParentMenuId())){
            menu.setGrade(Menu.GRADE_ROOT);
            int seq = menuDao.getRootGradeMaxSeq() + 1;
            String code = String.format("%03d",seq);
            menu.setSeq(seq)
                    .setCode(code);
        }else {
            Menu parent = queryById(Menu.class,ro.getParentMenuId());
            menu.setGrade(parent.getGrade()+1)
                    .setParentMenu(parent);
            int seq = menuDao.getMaxSeqByGradeAndParentMenu(parent.getGrade()+1,parent.getId()) + 1;
            String code = parent.getCode() + String.format("%03d",seq);
            menu.setSeq(seq)
                    .setCode(code);
        }
        menu.setName(ro.getName())
                .setIcon(ro.getIcon())
                .setUrl(ro.getUrl());
        if(ro.getStatus() == null){
            menu.setStatus(Menu.STATUS_ENABLE);
        }else {
            menu.setStatus(ro.getStatus());
        }
        save(menu);
        return menu;
    }

    @Override
    public Integer deleteIncludeChildren(String id) throws Exception{
        Menu target = queryById(Menu.class,id);
        if(target == null){
            throw new RuntimeException("查询不到菜单");
        }
        List<Menu> deleteList = menuDao.queryByCodeLike(target.getCode()+"%");
        List<String> idList = StreamUtil.extractFieldToList(deleteList,Menu::getId);
        String sql = "delete from sec_auth_role_menu where menu_id in ?1";
        baseDao.updateBySql(sql,idList);
        batchDelete(deleteList);
        return deleteList.size();
    }

}