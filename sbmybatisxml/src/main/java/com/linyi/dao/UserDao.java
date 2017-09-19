package com.linyi.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linyi.configure.Page;
import com.linyi.domain.User;

@Component
public class UserDao {

	@Autowired
	private SqlSession sqlSession;

//  private SqlSession sqlSession;
//	
//	public UserDao(SqlSession sqlSession) {
//		this.sqlSession = sqlSession;
//	}
	
	public List<User> findAll() {
		return this.sqlSession.selectList("findAll");
	}
	
	public List<User> findAll(Page<User> p) {
		return this.sqlSession.selectList("findAll", p);
	}
	
	public User selectUserById(long id) {
		return this.sqlSession.selectOne("selectUserById", id);
	}

}