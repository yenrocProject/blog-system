package com.yenroc.ho.blogic.restDto.album.photoUpdate;

import lombok.Data;

import java.io.Serializable;

@Data
public class PhotoUpdateResp implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    private Integer id;

    private String photoUrl;
}
