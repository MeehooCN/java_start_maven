package com.meehoo.biz.core.basic.domain.bos;

import com.meehoo.biz.common.util.LocalFileUtil;
import com.meehoo.biz.core.basic.annotation.SerialNumber;
import com.meehoo.biz.core.basic.annotation.SetBySystem;
import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 附件
 * Created by CZ on 2018/8/20.
 */
@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
public class AttachFile extends IdEntity {

    private static final String[] EXTENSIONS_DOCUMENT = {".txt", ".doc", ".docx", ".htm", ".html", ".pdf", "xls", "xlsx", ".ppt", ".pptx"};
    private static final String[] EXTENSIONS_IMAGE = {".bmp", ".jpg", ".jpeg", ".png", ".gif", ".ico",".webp", ".tif", ".pcx", "tga", ".exif", ".fpx", ".svg", ".psd", ".cdr"};
    private static final String[] EXTENSIONS_AUDIO = {".amr",".mp3", ".wav", ".wma",".au", ".OGG", ".ape", ".flac", ".acc", ".cda", ".MID", ".MIDI", ".RMI", ".XMI"};
    private static final String[] EXTENSIONS_VIDEO = {".avi", ".wmv", ".asf", ".asx", ".rmvb", ".rm", ".flash", ".mp4", ".mov", ".3gp", ".mpg", ".mpeg", ".flv", ".mkv", ".f4v", ".m4v", ".mts", ".vob"};

    //文档类型
    public static final int FILETYPE_DOCUMENT = 1;

    //图片类型
    public static final int FILETYPE_IMAGE = 2;

    //音频类型
    public static final int FILETYPE_AUDIO = 3;

    //视频类型
    public static final int FILETYPE_VIDEO = 4;

    //其它类型
    public static final int FILETYPE_UNKNOWN = 5;


//    public static final int FILE_STATUS_ENABLE = 11;
//
//    public static final int FILE_STATUS_DISABLE = 12;
//
//    @Column(columnDefinition = "INT default 11") //默认启用
//    private int status;

    /**
     * 编码
     */
    @SerialNumber
    @Column(length = 50)
    private String code;

    /**
     * 文件名
     */
    @Column(length = 100)
    private String fileName;

    /**
     * 文件大小
     */
    @Column
    private Long fileSize;

//    /**
//     * 序号
//     */
//    @Column
//    private int seq;

    /**
     * 文件类型
     */
    @Column
    private int fileType;

    /**
     * 源文件存储路径
     */
    @Column
    private String sourceUrl;

    /**
     * (高清大图/视频)缩略图路径
     */
    @Column
    private String thumbnailUrl;

    /**
     * 反应内容
     */
    @Column
    private String description;

    /**
     * 创建人Id
     */
    @Column(length = 50)
    private String uploadUserId;

    /**
     * 创建人姓名
     */
    @Column(length = 80)
    private String uploadUserName;

    /**
     * 创建（上传）时间
     */
    @Column
    @SetBySystem(createOnly = true)
    private Date createTime;

    private AttachFile inferFileType(String fileName) {
        final String extension = fileName.substring(fileName.lastIndexOf('.'));
        for (String ext : EXTENSIONS_DOCUMENT) {
            if (ext.equals(extension)) {
                this.fileType = FILETYPE_DOCUMENT;
                break;
            }
        }
//        if (fileType == 2) {
            for (String ext : EXTENSIONS_IMAGE) {
                if (ext.equals(extension)) {
                    this.fileType = FILETYPE_IMAGE;
                    break;
                }
            }
//        }
//        if (fileType == 3) {
            for (String ext : EXTENSIONS_AUDIO) {
                if (ext.equals(extension)) {
                    this.fileType = FILETYPE_AUDIO;
                    break;
                }
            }
//        }
//        if (fileType == 4) {
            for (String ext : EXTENSIONS_VIDEO) {
                if (ext.equals(extension)) {
                    this.fileType = FILETYPE_VIDEO;
                    break;
                }
            }
//        }
//        if (fileType == 5) {
            this.fileType = FILETYPE_UNKNOWN;
//        }
        return this;
    }

    public String getFileTypeShow() {
        switch (fileType) {
            case FILETYPE_DOCUMENT:
                return "文档";
            case FILETYPE_IMAGE:
                return "图片";
            case FILETYPE_AUDIO:
                return "音频";
            case FILETYPE_VIDEO:
                return "视频";
            case FILETYPE_UNKNOWN:
                return "其他";
            default:
                return "";
        }
    }

    public AttachFile setFileName(String fileName) {
        this.fileName = fileName;
        inferFileType(fileName);
        return this;
    }

    public String getSuffix(){
        if(fileName!=null){
            return fileName.substring(fileName.lastIndexOf('.'));
        }
        return "";
    }

    public String getThumbnailUrl() {
        return thumbnailUrl==null?"":thumbnailUrl;
    }

    public String getSourceUrl() {
        return sourceUrl==null?"":sourceUrl;
    }

    public String getFileNameWithoutSuffix() {
        return fileName==null?"":fileName.replace(getSuffix(),"");
    }

    public Long getFileSize() {
        return fileSize==null?0L:fileSize;
    }

    public String getRelativePath(){
        if(sourceUrl!=null){
            String[] splits = sourceUrl.split("/");
            if(splits.length>1){
                return splits[splits.length-2]+"/"+splits[splits.length-1];
            }
        }
        return "";
    }

    public void deleteRealFile(){
        LocalFileUtil.deleteFile(sourceUrl);
        LocalFileUtil.deleteFile(thumbnailUrl);
//        LocalFileUtil.deleteFile(voiceUrl);
    }

    @Override
    public int hashCode() {
        if(id!=null){
            int hashCode = id.hashCode();
            if(hashCode<0){
                hashCode = -hashCode;
            }
            return hashCode;
        }
        return 0;
    }

}
