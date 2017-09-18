package com.linyi.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	public User selectUserById(long id) {
		return this.sqlSession.selectOne("selectUserById", id);
	}

}