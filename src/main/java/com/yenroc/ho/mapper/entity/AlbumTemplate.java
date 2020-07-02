package com.yenroc.ho.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_template")
public class AlbumTemplate implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Column(name = "id")
    @Id
    private Integer id;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "template_desc")
    private String templateDesc;


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
}
