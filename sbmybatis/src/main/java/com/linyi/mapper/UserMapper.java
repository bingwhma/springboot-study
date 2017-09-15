package com.linyi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.linyi.domain.User;

@Mapper
public interface UserMapper {
	
	@Select("select * from user where id = #{id} ")
	public User findById(int id);

	@Select("select * from user where name like CONCAT('%',#{name},'%') ")
    public List<User> findByName(String name);
}
