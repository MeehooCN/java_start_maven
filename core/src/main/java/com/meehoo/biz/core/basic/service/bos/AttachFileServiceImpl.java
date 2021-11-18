package com.meehoo.biz.core.basic.service.bos;

import com.meehoo.biz.common.util.LocalFileUtil;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.service.common.ICommonService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by cz on 2018/08/28.
 */
@Service
@Transactional
public class AttachFileServiceImpl extends BaseService implements IAttachFileService, ApplicationRunner {

    private final ICommonService commonService;

    //统一压缩图片的宽
    private static final int IMAGE_WIDTH = 400;
    //统一压缩图片的高
    private static final int IMAGE_HEIGHT = 300;

    public AttachFileServiceImpl(ICommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LocalFileUtil.init();
    }
}
