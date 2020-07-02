package com.yenroc.ho.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_photo")
public class AlbumPhoto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Column(name = "id")
    @Id
    private Integer id;

    @Column(name = "album_id")
    private String albumId;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "template_photo_id")
    private Integer templatePhotoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getTemplatePhotoId() {
        return templatePhotoId;
    }

    public void setTemplatePhotoId(Integer templatePhotoId) {
        this.templatePhotoId = templatePhotoId;
    }
}
