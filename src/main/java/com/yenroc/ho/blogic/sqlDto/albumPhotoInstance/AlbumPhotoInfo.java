package com.yenroc.ho.blogic.sqlDto.albumPhotoInstance;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlbumPhotoInfo implements Serializable {

    private static final long serialVersionUID = -2319665209202431010L;

    private Integer albumPhotoInstanceId;

    private String fileId;

    private String fileName;

    private String photoUrl;

    private String photoConfigName;

    private String photoDesc;

    private Integer photoHeight;

    private Integer photoWidth;

}
