package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.AlbumTemplatePhotoConfig;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface AlbumTemplatePhotoConfigDao extends BaseMapper<AlbumTemplatePhotoConfig> {
    
    List<AlbumTemplatePhotoConfig> findByTemplateId(Integer templateId);
}
