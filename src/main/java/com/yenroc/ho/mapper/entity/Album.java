package com.yenroc.ho.mapper.entity;


import javax.persistence.*;
import java.io.Serializable;

@Table(name="album")
public class Album implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "album_user_id")
    private Integer albumUserId;

    @Column(name = "album_template_id")
    private Integer albumTemplateId;

    @Column(name = "album_name")
    private String albumName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAlbumUserId() {
        return albumUserId;
    }

    public void setAlbumUserId(Integer albumUserId) {
        this.albumUserId = albumUserId;
    }

    public Integer getAlbumTemplateId() {
        return albumTemplateId;
    }

    public void setAlbumTemplateId(Integer albumTemplateId) {
        this.albumTemplateId = albumTemplateId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
