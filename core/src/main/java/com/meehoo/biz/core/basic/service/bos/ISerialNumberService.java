package com.meehoo.biz.core.basic.service.bos;


import com.meehoo.biz.core.basic.service.IBaseService;

import java.util.Map;

/**
 * Created by Administrator on 2016-06-01.
 */
public interface ISerialNumberService  extends IBaseService {
    public Map<String,Object> getBizObjectMaxSeq(String bizObject);
}
