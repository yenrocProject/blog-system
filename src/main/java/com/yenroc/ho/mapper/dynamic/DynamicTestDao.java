package com.yenroc.ho.mapper.dynamic;

import com.yenroc.ho.mapper.dynamic.entity.TestDemo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author： heyanpeng
 * @date： 2021/7/20
 */
//@Mapper
public interface DynamicTestDao extends BaseMapper<TestDemo> {
}
