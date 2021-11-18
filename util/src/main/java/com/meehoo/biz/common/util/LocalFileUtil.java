package com.meehoo.biz.common.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.UUID;

public class LocalFileUtil {

    private static final String[] EXTENSIONS_DOCUMENT = {".txt", ".doc", ".docx", ".htm", ".html", ".pdf", "xls", "xlsx", ".ppt", ".pptx"};
    private static final String[] EXTENSIONS_IMAGE = {".bmp", ".jpg", ".jpeg", ".png", ".gif", ".ico",".webp", ".tif", ".pcx", "tga", ".exif", ".fpx", ".svg", ".psd", ".cdr"};
    private static final String[] EXTENSIONS_AUDIO = {".amr", ".mp3", ".wav", ".wma", ".au", ".OGG", ".ape", ".flac", ".acc", ".cda", ".MID", ".MIDI", ".RMI", ".XMI"};
    private static final String[] EXTENSIONS_VIDEO = {".avi", ".wmv", ".asf", ".asx", ".rmvb", ".rm", ".flash", ".mp4", ".mov", ".3gp", ".mpg", ".mpeg", ".flv", ".mkv", ".f4v", ".m4v", ".mts", ".vob"};

    private static Path basePath;

    //统一压缩图片的宽
    private static final int IMAGE_WIDTH = 400;
    //统一压缩图片的高
    private static final int IMAGE_HEIGHT = 300;

    public static void init() {
        ResourceBundle bundle = ResourceBundle.getBundle("uploadPath");
        final String basePathStr = bundle.getString("picDiskPath");
        if (StringUtil.stringNotNull(basePathStr)) {
            LocalFileUtil.basePath = Paths.get(basePathStr);
            try {
                Files.createDirectories(basePath);
                Files.createDirectories(basePath.resolve("image"));
                Files.createDirectories(basePath.resolve("video"));
                Files.createDirectories(basePath.resolve("audio"));
                Files.createDirectories(basePath.resolve("document"));
                Files.createDirectories(basePath.resolve("unknown"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("basePath不能为空");
        }
    }

    public static String uploadFile(final String fileName, byte[] fileContent) throws IOException {
        if (StringUtil.stringNotNull(fileName) && fileContent != null) {
            final String fileExt = fileName.substring(fileName.lastIndexOf('.'));
            final String fileType = getFileType(fileExt);
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            String diskFileName = uuid + System.currentTimeMillis() + fileExt;
            Path fileSavePath = basePath.resolve(fileType).resolve(diskFileName);
            File diskFile = fileSavePath.toFile();
            try (FileOutputStream fos = new FileOutputStream(diskFile)) {
                fos.write(fileContent);
                fos.flush();
            }
//            if (".amr".equals(fileExt)) {
//                File mp3File = new File(fileSavePath.toString().replace(fileExt, ".mp3"));
//                if(JavaCVUtil.convertAudioFileToMp3(diskFile,mp3File)){
//                    diskFile.delete();
//                    diskFileName = diskFileName.replace(fileExt,".mp3");
//                }
//            }

            return "static/resfile/" + fileType + "/" + diskFileName;

        }
        return "";
    }

    public static String createAndUploadThumbnailForPhoto(InputStream inputStream,long size,String fileName, String sourceUrl) throws IOException {
        //如果图片大小在10kb以内，则返回原图的地址
        if (size < 10240) {
            return sourceUrl;
        }
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Thumbnails.of(inputStream)
                    .size(IMAGE_WIDTH, IMAGE_HEIGHT)
                    .toOutputStream(baos);
            return LocalFileUtil.uploadFile(fileName, baos.toByteArray());
        }
    }

    public static String getSourceUrl(final String sourceUrl) {
        int lastSplitIndex = sourceUrl.lastIndexOf("/");
        if (lastSplitIndex > -1) {
            String fileName = sourceUrl.substring(lastSplitIndex + 1);
            String fileType = sourceUrl.substring(0, lastSplitIndex);
            lastSplitIndex = fileType.lastIndexOf('/');
            if (lastSplitIndex > -1) {
                fileType = fileType.substring(lastSplitIndex + 1);
                Path filePath = LocalFileUtil.basePath.resolve(fileType).resolve(fileName);
                File file = filePath.toFile();
                String path = file.getPath();
                return  path;
            }
        }
        return null;
    }

    public static byte[] downloadFile(final String sourceUrl) {
        int lastSplitIndex = sourceUrl.lastIndexOf(File.separator);
        if (lastSplitIndex > -1) {
            String fileName = sourceUrl.substring(lastSplitIndex + 1);
            String fileType = sourceUrl.substring(0, lastSplitIndex);
            lastSplitIndex = fileType.lastIndexOf(File.separator);
            if (lastSplitIndex > -1) {
                fileType = fileType.substring(lastSplitIndex + 1);
                return downloadFile(fileType, fileName);
            }
        }
        return null;
    }

    public static byte[] downloadFile(final String fileType, final String fileName) {
        if (StringUtil.stringNotNull(fileType) && StringUtil.stringNotNull(fileName)) {
            Path filePath = LocalFileUtil.basePath.resolve(fileType).resolve(fileName);
            File file = filePath.toFile();
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] fileContent = new byte[fis.available()];
                    fis.read(fileContent);
                    return fileContent;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean deleteFile(String sourceUrl) {
        if (StringUtil.stringNotNull(sourceUrl)) {
            int lastSplitIndex = sourceUrl.lastIndexOf("/");
            if (lastSplitIndex > -1) {
                String fileName = sourceUrl.substring(lastSplitIndex + 1);
                String fileType = sourceUrl.substring(0, lastSplitIndex);
                lastSplitIndex = fileType.lastIndexOf('/');
                if (lastSplitIndex > -1) {
                    fileType = fileType.substring(lastSplitIndex + 1);
                    return deleteFile(fileType, fileName);
                }
            }
        }
        return true;
    }

    public static boolean deleteFile(final String fileType, final String fileName) {
        Path filePath = LocalFileUtil.basePath.resolve(fileType).resolve(fileName);
        File file = filePath.toFile();
        return !file.exists() || !file.isFile() || file.delete();
    }

    private static String getFileType(String fileExt) {
        for (String ext : EXTENSIONS_DOCUMENT) {
            if (ext.equalsIgnoreCase(fileExt)) {
                return "document";
            }
        }
        for (String ext : EXTENSIONS_IMAGE) {
            if (ext.equalsIgnoreCase(fileExt)) {
                return "image";
            }
        }
        for (String ext : EXTENSIONS_AUDIO) {
            if (ext.equalsIgnoreCase(fileExt)) {
                return "audio";
            }
        }
        for (String ext : EXTENSIONS_VIDEO) {
            if (ext.equalsIgnoreCase(fileExt)) {
                return "video";
            }
        }
        return "unknown";
    }

}
