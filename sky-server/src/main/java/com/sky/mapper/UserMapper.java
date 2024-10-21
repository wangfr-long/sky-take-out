package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.User;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from sky_take_out.user where openid=#{openid}")
    public User selectByOpenId(String openid);
    @Options(keyProperty = "id",useGeneratedKeys = true)
    @Insert("insert into sky_take_out.user (openid, name, phone, sex, id_number, avatar, create_time) VALUES " +
            "(#{openid}, #{name}, #{phone}, #{sex}, #{idNumber}, #{avatar}, now())")
    void insert(User user2);
}
