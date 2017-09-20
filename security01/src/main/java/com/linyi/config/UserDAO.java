package com.linyi.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Bean
    public JdbcTemplate getJdbcTemplate() {
    	return new JdbcTemplate(dataSource);
    }
    
    public UserInfo getUserInfo(String username){
    	String sql = "SELECT u.username name, u.password pass, group_concat(r.role) role FROM "+
    			     "users u INNER JOIN user_roles r on u.username=r.username WHERE "+
    			     "u.enabled =1 and u.username = ?";
    	UserInfo userInfo = (UserInfo)jdbcTemplate.queryForObject(sql, new Object[]{username},
    		new RowMapper<UserInfo>() {
	            public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	                UserInfo user = new UserInfo();
	                user.setUsername(rs.getString("name"));
	                user.setPassword(rs.getString("pass"));
	                user.setRole(Arrays.asList(rs.getString("role").split(",")));
	                return user;
	            }
        });
    	
    	return userInfo;
    }
}
