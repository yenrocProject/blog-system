package com.yenroc.ho.blogic.restDto.album.createOrUpdate;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateOrUpdateAlbumReqt implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    private Integer albumId;

    private String albumDesc;

    private String albumName;

    private String albumStyleCss;

    private Integer albumTemplateId;

    private Integer privateView;

    private Integer userId;

    private List<CreateOrUpdateAlbumReqtM01> albumPhotos;

}
