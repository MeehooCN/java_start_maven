package com.meehoo.biz.core.basic.service.setting;


import com.meehoo.biz.core.basic.domain.security.User;

import java.util.List;

/**
 * Created by wangjian on 2017-12-05.
 */
public interface IOptLogService{
    /**
     * 保存操作日志
     * @param admin  操作人
     * @param opt  操作内容
     * @param optParams 操作内容的参数,对opt进行填充
     * @throws Exception
     */
    void saveLog(User admin, String opt, Object... optParams)throws Exception;

    void batchDelete(List<String> idList);
}
