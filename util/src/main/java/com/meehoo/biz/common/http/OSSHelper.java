package com.meehoo.biz.common.http;

//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.model.PutObjectResult;

/**
 * Created by Wanghan on 2017/2/8.
 */
public class OSSHelper {

    private static String endpoint = "oss-cn-chengdu.aliyuncs.com";

    private static String accessKeyId = "LTAI4FdzV7deHN3bYdaNqpkw";
    private static String accessKeySecret = "eGQkGGkz6Q4wLlGLqLZsgBlsDAGRO0";

    private static String bucketName = "magicmirror-photo";

    private static String firstKey = "my-first-key";

    private static String url = "http://"+bucketName+"."+endpoint+"/";
//
//
//    public static Map<String, String> uploadFile(MultipartFile file){
//        Map<String, String> returnMap = new HashMap<>();
//        OSSClient ossClient = new OSSClient("http://"+endpoint, accessKeyId, accessKeySecret);
//        try {
//
//            String fileFullName = file.getOriginalFilename();
//            String fileExtName = fileFullName.substring(fileFullName.lastIndexOf(".")+1);
//            String ossFileName = NumberUtil.createTimeNumber("img") + "." + fileExtName;
//            InputStream is = file.getInputStream();
//
//            ossClient.putObject(bucketName, ossFileName, is);
//
//            String returnUrl = url + ossFileName;
//
//            returnMap.put("url", returnUrl);
//            returnMap.put("fileName", ossFileName);
//            returnMap.put("bucketName", bucketName);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            ossClient.shutdown();
//        }
//        return returnMap;
//    }
//
//    public static void main(String[] args){
//        File file = new File("F:\\IMG_9256.jpg");
//
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        PutObjectResult pr =ossClient.putObject(bucketName, NumberUtil.createTimeNumber("img"), file);
//        System.out.println(pr.getETag());
//    }

}
