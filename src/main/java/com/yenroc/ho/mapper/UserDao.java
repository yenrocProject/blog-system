package com.yenroc.ho.mapper;

import com.yenroc.ho.mapper.entity.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

    List<User> finByUserName(String userName);
}
