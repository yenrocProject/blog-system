package com.yenroc.ho.mapper;

import com.yenroc.ho.blogic.sqlDto.albumPhotoInstance.AlbumPhotoInfo;
import com.yenroc.ho.mapper.entity.AlbumPhotoInstance;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface AlbumPhotoInstanceDao extends BaseMapper<AlbumPhotoInstance> {

    List<AlbumPhotoInfo> getPhotoInfoByAlbumId(Integer albumId);
}
