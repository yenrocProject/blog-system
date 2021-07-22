package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.base.BlogCommonMapper;
import com.yenroc.ho.mapper.entity.Demo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoDao extends BlogCommonMapper<Demo> {
    
    // 自定义查询
    List<Demo> queryDemo(Demo demo);

}
