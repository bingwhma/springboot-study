package com.linyi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linyi.domain.User;
import com.linyi.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
    private UserMapper userDao;
   
    public List<User> likeName(String name){
        return userDao.findByName(name);
    }
}
