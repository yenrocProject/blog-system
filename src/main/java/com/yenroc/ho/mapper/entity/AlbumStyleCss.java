package com.yenroc.ho.mapper.entity;


import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="album_style_css")
public class AlbumStyleCss extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "option_name")
    private String optionName;

    @Column(name = "option_value")
    private String optionValue;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}
