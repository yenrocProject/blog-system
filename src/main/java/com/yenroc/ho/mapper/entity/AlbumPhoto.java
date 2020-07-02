package com.yenroc.ho.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_photo")
public class AlbumPhoto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Column(name = "id")
    private Integer id;

    @Column(name = "album_id")
    private Integer albumId;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "file_id")
    private String fileId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
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
}
