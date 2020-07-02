package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.AlbumTemplatePhotoConfig;
import com.yenroc.ho.mapper.entity.AlbumUser;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface AlbumUserDao extends BaseMapper<AlbumUser> {
    

}
