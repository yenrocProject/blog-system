package com.yenroc.ho.mapper.entity;


import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="album_instance")
public class AlbumInstance extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "album_template_id")
    private Integer albumTemplateId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "album_desc")
    private String albumDesc;

    @Column(name = "private_view")
    private Integer privateView;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "album_style_css")
    private String albumStyleCss;

    @Column(name = "default_view_photo")
    private String defaultViewPhoto;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumDesc() {
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    public Integer getPrivateView() {
        return privateView;
    }

    public void setPrivateView(Integer privateView) {
        this.privateView = privateView;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAlbumStyleCss() {
        return albumStyleCss;
    }

    public void setAlbumStyleCss(String albumStyleCss) {
        this.albumStyleCss = albumStyleCss;
    }

    public String getDefaultViewPhoto() {
        return defaultViewPhoto;
    }

    public void setDefaultViewPhoto(String defaultViewPhoto) {
        this.defaultViewPhoto = defaultViewPhoto;
    }
}
