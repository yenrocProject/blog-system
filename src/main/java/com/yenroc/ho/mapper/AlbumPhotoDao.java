package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.Album;
import com.yenroc.ho.mapper.entity.AlbumPhoto;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface AlbumPhotoDao extends BaseMapper<AlbumPhoto> {
    

}
