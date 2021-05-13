package com.yenroc.ho.vo;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 描述:共通的上传路径DTO，包含真实路径与虚拟路径
 * @version:1.0.0
 * @author: 和彦鹏
 * @date:2020年11月24日
 */
@Getter
@Setter
public class CommonUploadResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原始文件虚拟地址
     */
    @ApiModelProperty(value = "文件路径")
    private String fileFullPath;

    /**
     * 文件缩略图地址
     */
    @ApiModelProperty(value = "文件缩略图地址")
    private String thumbFullPath;

    /**
     * 文件真实名称
     */
    @ApiModelProperty(value = "文件原始文件名")
    private String fileOriginalName;

    /**
     * 文件大小，单位：字节
     */
    @ApiModelProperty(value = "文件大小，单位：字节")
    private Long fileSize;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 附加信息：比如mongodb的objectId
     */
    @ApiModelProperty(value = "附加信息：比如mongodb的objectId")
    private JsonNode metaData;

    /**
     * fileObj对象存储 返回的主键 id
     */
    @ApiModelProperty(value = "fileObj对象存储 返回的主键 id")
    private String fileId;
}
