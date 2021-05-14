package com.yenroc.ho.blogic.restDto.album.viewAlbum;

import lombok.Data;

@Data
public class AlbumPhotoInfoVo {

    // 照片实例Id
    private Integer albumPhotoInstanceId;

    // 文件名
    private String fileName;

    // 文件路径
    private String photoUrl;

}
