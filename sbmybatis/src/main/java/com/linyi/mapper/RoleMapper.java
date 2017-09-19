package com.linyi.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.linyi.domain.Role;

@Mapper
public interface RoleMapper {
	
	@Insert("insert into Role(id, role) values(#{id}, #{role})")
	public int saveRole(Role role);

}
