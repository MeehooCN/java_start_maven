package com.meehoo.biz.mobile.basic;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.param.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author zc
 * @date 2018-07-13
 */
@RestController
public class FileController {
    String picDiskPath ;
    String docPath ;

    @Autowired
    public FileController() {
        ResourceBundle bundle = ResourceBundle.getBundle("uploadPath");

        picDiskPath = bundle.getString("picDiskPath");
        docPath = bundle.getString("docpath");

        File pic = new File(picDiskPath);
        if (!pic.exists())
            pic.mkdirs();

        File doc = new File(docPath);
        if (!doc.exists())
            doc.mkdirs();

    }

    /**
     * 图片上传
     * @return
     */
    @RequestMapping(value="uploadPic/uploadFile",method = RequestMethod.POST)
    public HttpResult<String> uploadFile(HttpServletRequest request, HttpServletResponse response){
        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile fileUpload = multipartRequest.getFiles("img").get(0);
                if(!fileUpload.isEmpty()){
                    //图片保存
                    Map<String, String> retMap = uploadFile(fileUpload,0);
                    String saveUrl = retMap.getOrDefault("url", "");
                    if(!StringUtil.stringNotNull(saveUrl)){
                        throw new RuntimeException("保存图片失败");
                    }else{
                        return HttpResult.success(saveUrl);
                    }
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

    /**
     * 保存文件到本地磁盘
     * @param fileUpload
     * @param type 0 图片    1 文件
     * @return
     * @throws Exception
     */
    private Map<String,String> uploadFile(MultipartFile fileUpload, int type) throws Exception{
        Map<String,String> retMap = new HashMap<>();
        // 判断文件是否存在
        if (!fileUpload.isEmpty()) {
            //图片存本地
            String filePath=type==0?picDiskPath:docPath;
            File dir=new File(filePath);
            if(!dir.isDirectory())
                dir.mkdir();

            String fileOriginalName=fileUpload.getOriginalFilename();
            String newFileName;
//            if (type == 0) {
            newFileName = BaseUtil.createTimeNumber("")+fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
//            }else{
//                newFileName = fileUpload.getOriginalFilename();
//            }
//            String newFileName = fileUpload.getOriginalFilename();
            File file=new File(filePath+newFileName);
            //文件写入磁盘
            fileUpload.transferTo(file);
            //返回存储的相对路径+文件名称
            String returnFilePath = (type==0?"image?name=":"")+ newFileName;

            retMap.put("url",returnFilePath);
            retMap.put("fileName",fileOriginalName);

        }
        return retMap;
    }
    /**读取图片*/
    @RequestMapping(value = {"/image/{name}"}) //映射多个URL
    @ResponseBody
    public void getImage(HttpServletResponse response, @PathVariable String name) throws Exception{
        String JPG="image/jpeg;charset=GB2312";

        if(StringUtil.stringNotNull(name)){
          //  name.replaceAll("$",File.separator);
            // 本地文件路径
            String filePath = picDiskPath+ StringUtils.replace(name, "$", File.separator);
            File file = new File(filePath);
            if (file.exists()){
                // 获取输出流
                OutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(file);
                // 读数据
                byte[] data = new byte[fileInputStream.available()];
                fileInputStream.read(data);
                fileInputStream.close();
                // 回写
                response.setContentType(JPG);
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}
