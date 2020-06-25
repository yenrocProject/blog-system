package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.Demo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface DemoDao extends BaseMapper<Demo> {
    
    // 自定义查询
    List<Demo> queryDemo(Demo demo);

}
