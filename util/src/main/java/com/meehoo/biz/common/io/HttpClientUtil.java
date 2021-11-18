package com.meehoo.biz.common.io;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.bos.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;

/**
 * Created by Wanghan on 2016/5/24.
 */
public class HttpClientUtil {

//    public static String post(String url, String param){
//        String resultStr = "";
//
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(url);
//
//        try {
//            HttpEntity bos = new StringEntity(param, "text/plain", "UTF-8");
//            httpPost.setEntity(bos);
//            HttpResponse response = httpClient.execute(httpPost);
//            HttpEntity rentity = response.getEntity();
//            if(rentity != null){
//                resultStr = EntityUtils.toString(rentity, "UTF-8");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            httpPost.abort();
//        }
//
//        return resultStr;
//    }
//
//    public static String get(String url){
//        String resultStr = "";
//
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(url);
//
//        try {
//            HttpResponse response = httpClient.execute(httpGet);
//            HttpEntity rentity = response.getEntity();
//            if(rentity != null){
//                resultStr = EntityUtils.toString(rentity, "UTF-8");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            httpGet.abort();
//        }
//
//        return resultStr;
//    }
//
//    public static void main(String[] args) {
//        String r = post("http://222.177.141.83:51120/api",
//                " {\n" +
//                        "    \"cmd\": \"getSupportPrescriptionHospital\",\n" +
//                        "    \"token\": \"f9c5c192bb4b90dba2ebf521aa642048\",\n" +
//                        "\"params\": {\n" +
//                        "\"page\": {\n" +
//                        "            \"pageSize\": \"5\", //ÿҳ��ѯ��¼��(��󲻵ó���20)\n" +
//                        "            \"currentPage\": \"1\" //��ѯ�ڼ�ҳ(1��ʾ��һҳ)\n" +
//                        "    }\n" +
//                        "    }\n" +
//                        "}");
//        System.out.println(r);
//    }
}
