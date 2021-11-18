package com.meehoo.biz.core.basic.vo.bos;

import com.meehoo.biz.core.basic.domain.bos.Menu;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.security.MenuDefineVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zc
 * @date 2020-12-29
 */
@Setter
@Getter
public class MenuWithDefineVO extends MenuVO {
    private List<MenuDefineVO> menuDefineVOList;

//    private List<String> selectList;

    public MenuWithDefineVO(Menu menu){
        super(menu);
        this.menuDefineVOList = VOUtil.convertDomainListToTempList(menu.getMenuDefineList(),MenuDefineVO.class);
//        selectList = new ArrayList<>();
    }
}
