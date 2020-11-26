package com.yenroc.ho.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 描述:文件上传的VO
 * 因为该类包含InputString,所以ToString方法手动重写
 * @version:1.0.0
 * @author: 和彦鹏
 * @date:2020年11月24日
 */
@Getter
@Setter
public class CommonUploadFileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件Id
     */
    private String fileId;

    /**
     * 自动生产文件名称
     */
    private String fileName;

    /**
     * 文件后缀名
     */
    private String suffixName;
    
    /**
     * 上传的原始文件名
     */
    private String fileOriginalName;
    
    /**
     * 文件大小：单位字节
     */
    private Long fileSize;
    
    /**
     * 文件类型
     */
    private String fileType;
    
    /**
     * 文件数据流
     */
    private InputStream inputStream;
    
    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("上传的原始文件名：" + this.fileOriginalName + ",");
        sBuilder.append("上传的新文件名：" + this.fileName + ",");
        sBuilder.append("文件大小：" + this.fileSize + "字节,");
        sBuilder.append("文件后缀名：" + this.suffixName);
        sBuilder.append("文件类型：" + this.fileType);
        return sBuilder.toString();
    }
}
