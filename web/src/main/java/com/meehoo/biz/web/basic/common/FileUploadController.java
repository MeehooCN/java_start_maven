package com.meehoo.biz.web.basic.common;

import com.meehoo.biz.core.basic.param.HttpResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 王健 on 2017/17/11.
 * 图片上传
 */
@RestController
@RequestMapping("/uploadPic")
public class FileUploadController {
    /** 允许上传的扩展名*/
    private static final String [] extensionPermit = {"jpeg", "gif", "jpg","png"};


    private static String fileTypeData = "data";

    /**
     * 图片上传
     * @return
     */
    @RequestMapping(value="uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<String> uploadFile(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> returnMap = new HashMap<>();
        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile fileUpload = multipartRequest.getFiles("img").get(0);
                if(!fileUpload.isEmpty()){
                    //图片保存到oss
//                    Map<String, String> retMap = OSSHelper.uploadFile(fileUpload);
//                    String saveUrl = retMap.getOrDefault("url", "");
//                    if(!StringUtil.stringNotNull(saveUrl)){
//                        throw new RuntimeException("保存图片失败");
//                    }else{
//                        return HttpResult.success(saveUrl);
//                    }
                    return HttpResult.success();
                }else{
                    throw new RuntimeException("上传文件为空");
                }
            }else{
                throw new RuntimeException("上传失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.fail(e);
        }
    }

//    /**
//     * 上传图片
//     * for 富文本框
//     * 富文本框要求返回格式为：{'data': {'image_src': xxx} , status:'success'}
//     */
//    @PostMapping("uploadForUmEditor")
//    @ResponseBody
//    Map<String, Object> uploadForUmEditor(HttpServletRequest request,
//                                          HttpServletResponse response) {
//        Map<String, Object> returnMap = new HashMap<>();
//        try {
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
//            // 判断文件是否存在
//            if (!file.isEmpty()) {
//                //图片保存到oss
//                Map<String, String> retMap = OSSHelper.uploadFile(file);
//
//                if (!BaseUtil.objectNotNull(retMap)) {
//                    response.setStatus(416);
//                    returnMap.put("flag", "1");
//                    returnMap.put("msg", "保存图片失败");
//                }
//
//                String saveUrl = retMap.getOrDefault("url", "");
//                String saveFileId = retMap.getOrDefault("fileName", "");
//                String saveGroupId = retMap.getOrDefault("bucketName", "");
//
//                if (!BaseUtil.stringNotNull(saveUrl)) {
//                    response.setStatus(416);
//                    returnMap.put("flag", "1");
//                    returnMap.put("msg", "保存图片失败");
//                }
//
//                Map<String, String> dataMap = new HashMap<>();
//                dataMap.put("image_src",saveUrl);
//
//                returnMap.put("data", dataMap);
//                returnMap.put("status", "success");
//            }
//        } catch (Exception e) {
//            response.setStatus(500);
//            e.printStackTrace();
//            returnMap.put("status", "error");
//            returnMap.put("msg", "服务器内部错误");
//        }
//        return returnMap;
//    }
//    /**
//     * spring提供的上传方法
//     * @param request
//     * @return
//     * @throws IllegalStateException
//     * @throws IOException
//     */
//    @RequestMapping(value = "springUpload",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,Object>  springUpload(HttpServletRequest request, String type) throws IllegalStateException, IOException
//    {
//        Map<String,Object> returnMap = new HashMap<>();
//        try{
//
//            if(fileTypeData.equals(type)) {
//                String path = "/mdzz/bimfile/data";
//                returnMap = BaseUtil.springGetSaveUrl(request, path);
//            }
//            else {
//                returnMap.put("flag", "1");
//                returnMap.put("msg", "操作失败，缺少参数type");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            returnMap.put("flag", "1");
//            returnMap.put("msg", "操作失败");
//        }
//        return returnMap;
//    }
}
