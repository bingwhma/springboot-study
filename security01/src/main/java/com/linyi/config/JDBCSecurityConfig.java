package com.linyi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class JDBCSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

    @Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
          
        //jdbcAuthentication从数据库中获取，但是默认是以security提供的表结构  
        //usersByUsernameQuery 指定查询用户SQL  
        //authoritiesByUsernameQuery 指定查询权限SQL  
        auth.jdbcAuthentication()
        		.dataSource(dataSource)
        		.usersByUsernameQuery("select username,password, enabled from users where username=?")
        		.authoritiesByUsernameQuery("select username, role from user_roles where username=?"); 
          
        //注入userDetailsService，需要实现userDetailsService接口  
        //auth.userDetailsService(userDetailsService);  
    }  
      
    /**定义安全策略*/  
    @Override  
    protected void configure(HttpSecurity http) throws Exception {  
        http.authorizeRequests()//配置安全策略 
           .antMatchers("/hello").permitAll()//定义/请求不需要验证  
            .anyRequest().authenticated()//其余的所有请求都需要验证  
            .and()
//          .logout()
//        	.logoutUrl("/logout").invalidateHttpSession(true).permitAll()//定义logout不需要验证  
//            .and()
        .formLogin();//使用form表单登录  
    }  
}
