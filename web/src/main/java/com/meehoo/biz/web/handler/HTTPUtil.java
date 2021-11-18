package com.meehoo.biz.web.handler;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author zc
 * @date 2020-08-05
 */
public class HTTPUtil {
    public static void setResponse(HttpServletResponse response, String fileName, HSSFWorkbook wb){
        //响应到客户端
        try {
//            setResponseHeader(response, fileName);
//            if(request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
//                fileName = new String(fileName.getBytes("GB2312"),"ISO-8859-1");
//            }else{
//                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
//            }
            fileName = new String(fileName.getBytes(),"ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
//            response.addHeader("Pargam", "no-cache");
//            response.addHeader("Cache-Control", "no-cache");

            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
