package com.linyi.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.linyi.domain.User;

@Mapper
public interface UserMapper {

	public User findById(int id);
}
