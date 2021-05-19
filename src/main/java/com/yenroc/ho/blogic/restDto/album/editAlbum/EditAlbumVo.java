package com.yenroc.ho.blogic.restDto.album.editAlbum;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EditAlbumVo  implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    private Integer id;

    private Integer albumTemplateId;

    private Integer userId;

    private String albumName;

    private String albumDesc;

    private Integer templatePhotoSize;

    private Integer privateView;

    private String privateKey;

    private String albumStyleCss;

    private String defaultViewPhoto;

    private Integer musicId;

    private String musicName;

    // 照片实例
    private List<EditAlbumPhotoVo> albumPhotoVoList;

}
