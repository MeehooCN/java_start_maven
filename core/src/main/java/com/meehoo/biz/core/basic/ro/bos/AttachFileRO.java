package com.meehoo.biz.core.basic.ro.bos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by cz on 2018/08/27.
 *
 */
@Getter
@Setter
public class AttachFileRO {
    private String id;

    /**
     * 序号
     */
    private Integer seq;


    /**
     * 附件描述
     */
    @ApiModelProperty("附件描述")
    private String description;

    /**
     * 拍摄人
     */
    @ApiModelProperty("拍摄人")
    private String photographer;

    private int seriesPhotoType;// 套系图片类型，1 封面  2 图文说明  3 展示的图片系列

}