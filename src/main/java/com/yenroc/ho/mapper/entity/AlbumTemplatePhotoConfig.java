package com.yenroc.ho.mapper.entity;


import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_template_photo_config")
public class AlbumTemplatePhotoConfig extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "album_template_id")
    private Integer albumTemplateId;

    @Column(name = "photo_config_name")
    private String photoConfigName;

    @Column(name = "photo_desc")
    private Integer photoDesc;

    @Column(name = "template_photo_size")
    private Integer templatePhotoSize;

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

    public Integer getPhotoDesc() {
        return photoDesc;
    }

    public void setPhotoDesc(Integer photoDesc) {
        this.photoDesc = photoDesc;
    }

    public Integer getTemplatePhotoSize() {
        return templatePhotoSize;
    }

    public void setTemplatePhotoSize(Integer templatePhotoSize) {
        this.templatePhotoSize = templatePhotoSize;
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
