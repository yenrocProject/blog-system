package com.yenroc.ho.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_template_photo_config")
public class AlbumTemplatePhotoConfig implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Column(name = "id")
    @Id
    private Integer id;

    @Column(name = "album_template_id")
    private Integer albumTemplateId;

    @Column(name = "alpum_photo")
    private String alpumPhoto;

    @Column(name = "alpum_photo_name")
    private String alpumPhotoName;

    @Column(name = "alpum_photo_desc")
    private String alpumPhotoDesc;

    @Column(name = "alpum_photo_width")
    private Integer alpumPhotoWidth;

    @Column(name = "alpum_photo_height")
    private Integer alpumPhotoHeight;

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

    public String getAlpumPhoto() {
        return alpumPhoto;
    }

    public void setAlpumPhoto(String alpumPhoto) {
        this.alpumPhoto = alpumPhoto;
    }

    public String getAlpumPhotoName() {
        return alpumPhotoName;
    }

    public void setAlpumPhotoName(String alpumPhotoName) {
        this.alpumPhotoName = alpumPhotoName;
    }

    public String getAlpumPhotoDesc() {
        return alpumPhotoDesc;
    }

    public void setAlpumPhotoDesc(String alpumPhotoDesc) {
        this.alpumPhotoDesc = alpumPhotoDesc;
    }

    public Integer getAlpumPhotoWidth() {
        return alpumPhotoWidth;
    }

    public void setAlpumPhotoWidth(Integer alpumPhotoWidth) {
        this.alpumPhotoWidth = alpumPhotoWidth;
    }

    public Integer getAlpumPhotoHeight() {
        return alpumPhotoHeight;
    }

    public void setAlpumPhotoHeight(Integer alpumPhotoHeight) {
        this.alpumPhotoHeight = alpumPhotoHeight;
    }
}
