package com.linyi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.linyi.config.Page;
import com.linyi.domain.User;

@Mapper
public interface UserMapper {
	
	@Select("select * from user where id = #{id} ")
	public User findById(int id);

	@Select("select * from user order by id desc")
	public List<User> findAll();

	@Select("select * from user order by id desc")
	public List<User> page(Page<User> p);
	
	@Select("select * from user where name like CONCAT('%',#{name},'%') ")
    public List<User> findByName(String name);
	
	@Insert("insert into User(id, name, age, comment) values(#{id}, #{name}, #{age}, #{comment})")
	public int saveUser(User user);
}
