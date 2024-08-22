package com.parsons.mapper;

import com.parsons.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(User user);
    @Select("SELECT * FROM user_data")
    List<User> selectAllUsers();
}
