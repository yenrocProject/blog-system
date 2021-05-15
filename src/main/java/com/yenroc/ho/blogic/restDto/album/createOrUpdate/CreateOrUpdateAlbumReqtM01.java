package com.yenroc.ho.blogic.restDto.album.createOrUpdate;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateOrUpdateAlbumReqtM01 implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    private Integer id;

    private String photoConfigName;

    private String photoDesc;

    private Integer photoWidth;

    private Integer photoHeight;

    private String fileId;

    private String fileName;

    private String photoUrl;
}
