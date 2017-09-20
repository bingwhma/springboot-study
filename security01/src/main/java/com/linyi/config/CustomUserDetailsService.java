package com.linyi.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserInfo userInfo = userDAO.getUserInfo(username);
		if (userInfo == null) {
			throw new UsernameNotFoundException("not found");
		}

		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		for (String role : userInfo.getRole()) {
			GrantedAuthority authority = new SimpleGrantedAuthority(role);
			roles.add(authority);
		}

		UserDetails userDetails = (UserDetails) new User(userInfo.getUsername(), userInfo.getPassword(), roles);

		return userDetails;
	}

}
