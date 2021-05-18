package com.yenroc.ho.rest.music163.music;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MusicSongVo implements Serializable {

    private Integer id;

    private String name;

    private String mp3Url;

    private List<ArtistVo> artists;

    private AlbumVo album;
}
