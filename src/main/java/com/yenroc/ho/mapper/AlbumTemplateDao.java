package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.Album;
import com.yenroc.ho.mapper.entity.AlbumTemplate;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface AlbumTemplateDao extends BaseMapper<AlbumTemplate> {
    

}
