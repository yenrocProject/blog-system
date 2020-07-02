package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.Album;
import com.yenroc.ho.mapper.entity.Demo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface AlbumDao extends BaseMapper<Album> {
    

}
