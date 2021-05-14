package com.yenroc.ho.blogic.restDto.album.userAlbumInfo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class AlbumVo {

    // 模板Id
    private Integer templateId;

    // 模板名称
    private String templateName;

    // 模板描述
    private String templateDesc;

    // 模板预览的实例Id
    private Integer defaultInstanceId;

    // 模板的照片数量
    private Integer templatePhotoSize;

    // 用户的相册id
    private Integer userAlbumId;

    // 用户的相册名称
    private String albumName;

    // 用户的相册描述
    private String albumDesc;

    // 相册密钥key
    private String privateKey;

}
