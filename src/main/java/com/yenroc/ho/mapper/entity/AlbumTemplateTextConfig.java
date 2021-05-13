package com.yenroc.ho.mapper.entity;


import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_template_text_config")
public class AlbumTemplateTextConfig extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "album_template_id")
    private Integer albumTemplateId;

    @Column(name = "text_code_key")
    private String textCodeKey;

    @Column(name = "text_style")
    private String textStyle;

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

    public String getTextCodeKey() {
        return textCodeKey;
    }

    public void setTextCodeKey(String textCodeKey) {
        this.textCodeKey = textCodeKey;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }
}
