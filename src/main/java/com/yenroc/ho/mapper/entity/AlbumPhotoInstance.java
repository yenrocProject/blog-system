package com.yenroc.ho.mapper.entity;


import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_photo_instance")
public class AlbumPhotoInstance extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "album_instance_id")
    private Integer albumInstanceId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "template_id")
    private Integer templateId;

    @Column(name = "template_photo_id")
    private Integer templatePhotoId;

    @Column(name = "file_id")
    private String fileId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumInstanceId() {
        return albumInstanceId;
    }

    public void setAlbumInstanceId(Integer albumInstanceId) {
        this.albumInstanceId = albumInstanceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplatePhotoId() {
        return templatePhotoId;
    }

    public void setTemplatePhotoId(Integer templatePhotoId) {
        this.templatePhotoId = templatePhotoId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
