package com.yenroc.ho.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="album_user")
public class AlbumUser implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
