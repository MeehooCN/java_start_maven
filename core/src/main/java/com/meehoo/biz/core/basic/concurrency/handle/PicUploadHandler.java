package com.meehoo.biz.core.basic.concurrency.handle;

import com.meehoo.biz.common.util.StringUtil;

/**
 * 图片上传规则的处理
 * @author zc
 * @date 2020-12-26
 */
public class PicUploadHandler implements IHandler<String>{

//    public PicUploadHandler(ConcurrentManager configure) {
//        super(configure);
//    }

    @Override
    public void exe(String url) throws Exception {
        if (StringUtil.stringIsNull(url)||url.length()<5){
            throw new RuntimeException("图片路径不对");
        }
        Thread.sleep(10000);
    }

}
