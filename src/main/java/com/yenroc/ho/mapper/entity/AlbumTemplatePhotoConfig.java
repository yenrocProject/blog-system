package com.yenroc.ho.mapper.entity;


import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="album_template_photo_config")
public class AlbumTemplatePhotoConfig extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "album_template_id")
    private Integer albumTemplateId;

    @Column(name = "photo_config_name")
    private String photoConfigName;

    @Column(name = "photo_desc")
    private String photoDesc;

    @Column(name = "photo_width")
    private Integer photoWidth;

    @Column(name = "photo_height")
    private Integer photoHeight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumTemplateId() {
        return albumTemplateId;
    }

    public void setAlbumTemplateId(Integer albumTemplateId) {
        this.albumTemplateId = albumTemplateId;
    }

    public String getPhotoConfigName() {
        return photoConfigName;
    }

    public void setPhotoConfigName(String photoConfigName) {
        this.photoConfigName = photoConfigName;
    }

    public String getPhotoDesc() {
        return photoDesc;
    }

    public void setPhotoDesc(String photoDesc) {
        this.photoDesc = photoDesc;
    }

    public Integer getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(Integer photoWidth) {
        this.photoWidth = photoWidth;
    }

    public Integer getPhotoHeight() {
        return photoHeight;
    }

    public void setPhotoHeight(Integer photoHeight) {
        this.photoHeight = photoHeight;
    }
}
