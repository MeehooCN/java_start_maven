package com.meehoo.biz.core.basic.vo.bos;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.core.basic.domain.bos.AttachFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Created by cz on 2018/08/27.
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AttachFileVO {
    private String fileId;

    private String attachFileId;

    private String code;

    private String fileName;
    /**
     * 序号
     */
    private Integer seq;

    private String belongEntityId;

    private String belongBizId;

    /**
     * 文件类型
     */
    private Integer fileType;

    private String fileTypeShow;

    /**
     * 源文件存储路径
     */
    private String sourceUrl;

    /**
     * 缩略图路径
     */
    private String thumbnailUrl;

    /**
     * 附件描述
     */
    private String description;

    /**
     * 创建人Id
     */
    private String uploadUserId;

    /**
     * 创建姓名
     */
    private String uploadUserName;

    /**
     * 创建时间
     */
    private String createTime;

    public AttachFileVO(AttachFile attachFile) {
        this.attachFileId = attachFile.getId();
        this.code = attachFile.getCode();
        this.fileName = attachFile.getFileName();
        this.fileType = attachFile.getFileType();
        this.fileTypeShow = attachFile.getFileTypeShow();
        this.sourceUrl = attachFile.getSourceUrl();
        this.thumbnailUrl = attachFile.getThumbnailUrl();
        this.description = attachFile.getDescription();
        this.uploadUserId = attachFile.getUploadUserId();
        this.uploadUserName = attachFile.getUploadUserName();
        this.createTime = DateUtil.timeToString(attachFile.getCreateTime());
        this.fileId = attachFile.getId();
    }

}