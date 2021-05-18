package com.yenroc.ho.rest.music163.music;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MusicVo implements Serializable {

    private List<MusicSongVo> songs = null;

}
