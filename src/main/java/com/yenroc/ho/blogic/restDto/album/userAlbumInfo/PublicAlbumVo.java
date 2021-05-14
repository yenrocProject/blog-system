package com.yenroc.ho.blogic.restDto.album.userAlbumInfo;

import lombok.Data;

/**
 * 公众的相册
 */
@Data
public class PublicAlbumVo {

    // 模板Id
    private Integer templateId;

    // 用户的相册id
    private Integer albumId;

    // 用户的相册名称
    private String albumName;

    // 用户的相册描述
    private String albumDesc;

    // 相册用户
    private String albumUserName;

    private String albumStyleCss;

    private String defaultViewPhoto;

}
