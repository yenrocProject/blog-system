package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.AlbumInstance;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface AlbumInstanceDao extends BaseMapper<AlbumInstance> {

    List<AlbumInstance> finByUserId(Integer userId);

}