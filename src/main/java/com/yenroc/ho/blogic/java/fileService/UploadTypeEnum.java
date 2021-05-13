package com.yenroc.ho.blogic.java.fileService;

/**
 * 文件上传类型
 */
public enum UploadTypeEnum {
    mongodb, // 文档数据库
    fileSystem, // 文件系统
    aliyunoss, // 阿里云OSS
    amazons3, // 亚马逊S3
    ftp,   // ftp
}
