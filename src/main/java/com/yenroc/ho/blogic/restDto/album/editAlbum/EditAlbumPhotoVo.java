package com.yenroc.ho.blogic.restDto.album.editAlbum;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class EditAlbumPhotoVo implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    private Integer id;

    private String photoConfigName;

    private String photoDesc;

    private Integer photoWidth;

    private Integer photoHeight;

}
