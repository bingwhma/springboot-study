package com.linyi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linyi.config.Page;
import com.linyi.domain.Role;
import com.linyi.domain.User;
import com.linyi.mapper.RoleMapper;
import com.linyi.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userDao;

	@Autowired
	private RoleMapper roleDao;

	public List<User> likeName(String name) {
		return userDao.findByName(name);
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public List<User> page(Page<User> p) {
		return userDao.page(p);
	}

	@Transactional(rollbackFor=Exception.class)
	public void save() throws Exception {
		try {
			User u = new User();
			u.setId(30);
			u.setName("30x");

			userDao.saveUser(u);

			Role r = new Role();

			r.setId(20);
			r.setRole("role");

			roleDao.saveRole(r);
		} catch (Exception e) {
			throw new Exception("save db error!!", e);
		}

	}

}
