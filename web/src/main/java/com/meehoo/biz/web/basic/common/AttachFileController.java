package com.meehoo.biz.web.basic.common;

import com.meehoo.biz.core.basic.service.bos.IAttachFileService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CZ on 2018/8/28.
 */
@Api(tags = "附件管理")
@RestController
@RequestMapping("attachFile")
public class AttachFileController {

    private final IAttachFileService attachFileService;

    public AttachFileController(IAttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

}

