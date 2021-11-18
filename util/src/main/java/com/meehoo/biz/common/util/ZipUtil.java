package com.meehoo.biz.common.util;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by qx on 2019/3/19.
 */
public class ZipUtil {

    //根据用户id创建临时文件夹路径
    private static Map<String,String> tempPath = new HashMap<>();
    private static Map<String,String> zipPath = new HashMap<>();

    //路径待修改
    public final static String base = "C:/";
    public final static String name = "/火灾简易调查";
    public final static String zipFileName = "/火灾简易调查.zip";
    public static String basepath ="C:/mh/upload";
//    public final static String uploadPath = "C:/mh/upload/";

    private static ZipOutputStream out;
    private static BufferedOutputStream bos;
    private static BufferedInputStream bis;
    private static FileInputStream fis;
    private static FileOutputStream fos;

    static {//basepath获取根路径
        try{
//            Properties properties = new Properties();
//            String clazzPath = new File("").getCanonicalPath();
//            String path = clazzPath.substring(0, clazzPath.indexOf("fireInvestigation-server-refactor"));
//            fis = new FileInputStream(path+uploadPath);
//            fis = new FileInputStream(uploadPath);
//            properties.load(fis);
//            basepath = properties.getProperty("basepath");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     * @throws Exception
     */
    public static void closeResource() throws Exception{
        if(BaseUtil.objectNotNull(out)){
            out.close();
        }
        if(BaseUtil.objectNotNull(bos)){
            bos.close();
        }
        if (BaseUtil.objectNotNull(bis)){
            bis.close();
        }
        if(BaseUtil.objectNotNull(fis)){
            fis.close();
        }
        if(BaseUtil.objectNotNull(fos)){
            fos.close();
        }
    }

    /**
     * 根据调查员id创建临时文件夹
     * @param investorId
     */
    public static void buildPath(String investorId){
        String path = base+investorId+name;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        zipPath.put(investorId,base+investorId+zipFileName);
        tempPath.put(investorId,path);
    }

    public static String getTempPath(String investorId){
        return tempPath.get(investorId);
    }


//    public static void downloadZip(HttpServletResponse response,String investorId) throws Exception{
//        //压缩文件
//        zip(zipPath.get(investorId),getTempPath(investorId));
//        //响应给浏览器
//        ZipUtil.downloadLocal(response,zipPath.get(investorId));
//        //3.删除临时文件
//        ZipUtil.deleteDirectory(base+investorId);
//    }

    /**
     * Zip
     * @param zipFileName
     * @param tempPath
     * @throws Exception
     */
    public static void zip(String zipFileName,String tempPath) throws Exception
    {
        ZipOutputStream out = new ZipOutputStream( new FileOutputStream(zipFileName));
        //创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File sourceFile = new File(tempPath);
        //调用函数
        compress(out,bos,sourceFile,sourceFile.getName());

        bos.close();
        out.close();
    }

    /**
     *
     * @param out
     * @param bos
     * @param sourceFile
     * @param base      火灾简易事故调查
     * @throws Exception
     */
    private static void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws Exception
    {
        //如果路径为目录（文件夹）
        if(sourceFile.isDirectory())
        {
            File[] flist = sourceFile.listFiles();
            if(flist.length==0){//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
                out.putNextEntry(  new ZipEntry(base+"/") );
            }else{//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for(int i=0;i<flist.length;i++){
                    compress(out,bos,flist[i],base+"/"+flist[i].getName());
                }
            }
        }
        else{//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
            out.putNextEntry( new ZipEntry(base) );
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int tag;
            System.out.println(base);
            //将源文件写入到zip文件中
            while((tag=bis.read())!=-1){
                bos.write(tag);
            }

            bos.flush();
            bis.close();
            fos.close();
        }
    }

    /**
     * 临时存储
     * @param sourceFile
     * @param investorId
     * @throws IOException
     */
    public static void copyFile(String sourceFile, String investorId) throws IOException {
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            buildPath(investorId);
            String suffix = System.currentTimeMillis()+"."+ sourceFile.split("\\.")[1];
            inStream = new FileInputStream(sourceFile); //读入原文件
            fs = new FileOutputStream(tempPath.get(investorId)+"\\"+suffix);

            byte[] buffer = new byte[1444];
            int byteread = 0;
            while ( (byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }finally {
            inStream.close();
            fs.close();
        }

    }

//    private static void downloadLocal(HttpServletResponse response,String filePath) throws Exception {
//        // 下载本地文件
//        String fileName = filePath.substring(filePath.lastIndexOf("/")+1); // 文件的默认保存名
//        fileName = new String(fileName.getBytes());
//        // 读到流中
//        InputStream inStream = new FileInputStream(filePath);// 文件的存放路径
//        // 设置输出的格式
//        response.reset();
//        response.setContentType("application/x-msdownload;charset=utf-8");
//        fileName = URLEncoder.encode(fileName,"UTF-8");
//        response.setHeader("Content-disposition", "attachment; filename="+fileName);
//        // 循环取出流中的数据
//        byte[] b = new byte[100];
//        int len;
//        try {
//            while ((len = inStream.read(b)) > 0) {
//                response.getOutputStream().write(b, 0, len);
//            }
//            inStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName  要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    private static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName  要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }


    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {// 删除子文件
                flag = ZipUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }else if (files[i].isDirectory()) {// 删除子目录
                flag = ZipUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param url 相对路径
     * @return
     */
    public static String getPath(String url){
        String source = url.substring(url.indexOf("/")+1).substring(url.indexOf("/")+2);
        return ZipUtil.basepath+source;
    }
}
