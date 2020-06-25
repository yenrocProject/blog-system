package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * 文件数据操作类
 */
@Mapper
public interface FileInfoDao extends BaseMapper<FileInfo> {

    FileInfo findByFileName(String fileName);

}
