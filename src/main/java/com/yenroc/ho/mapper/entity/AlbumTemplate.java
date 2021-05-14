package com.yenroc.ho.mapper.entity;


import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_template")
public class AlbumTemplate extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "template_desc")
    private String templateDesc;

    @Column(name = "default_instance_id")
    private Integer defaultInstanceId;

    @Column(name = "template_photo_size")
    private Integer templatePhotoSize;

    @Column(name = "template_html_path")
    private String templateHtmlPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public Integer getDefaultInstanceId() {
        return defaultInstanceId;
    }

    public void setDefaultInstanceId(Integer defaultInstanceId) {
        this.defaultInstanceId = defaultInstanceId;
    }

    public Integer getTemplatePhotoSize() {
        return templatePhotoSize;
    }

    public void setTemplatePhotoSize(Integer templatePhotoSize) {
        this.templatePhotoSize = templatePhotoSize;
    }

    public String getTemplateHtmlPath() {
        return templateHtmlPath;
    }

    public void setTemplateHtmlPath(String templateHtmlPath) {
        this.templateHtmlPath = templateHtmlPath;
    }
}
