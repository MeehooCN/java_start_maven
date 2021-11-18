package com.meehoo.biz.mobile.handler;

import com.meehoo.biz.common.util.LocalFileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zc
 * @date 2020-01-13
 */
public class FileUtil {
    public static String uploadFile(MultipartFile multipartFile) throws IOException {
        return LocalFileUtil.uploadFile(multipartFile.getOriginalFilename(),multipartFile.getBytes());
    }
}
