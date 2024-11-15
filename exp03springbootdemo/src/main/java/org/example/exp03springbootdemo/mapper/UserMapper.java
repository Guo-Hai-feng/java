package org.example.exp03springbootdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.exp03springbootdemo.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectUserById(@Param("id") Integer id);
    List<User> selectAllUsers();

    int insertUser(User user);
    int updateUser(User user);
    int deleteUserById(@Param("id") Integer id);
}

