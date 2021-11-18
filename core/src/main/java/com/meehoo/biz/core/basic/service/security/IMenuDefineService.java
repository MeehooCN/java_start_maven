package com.meehoo.biz.core.basic.service.security;

import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.bos.AuthDefineVO;

/**
 * @author zc
 * @date 2020-12-23
 */
public interface IMenuDefineService extends IBaseService {
    AuthDefineVO getMenuWithDefines(String roleId);

    AuthDefineVO getDefineByMenu(String menuId, String roleId);

    boolean hasAuthDefine(String roleId, String defineId);
}
