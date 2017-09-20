package com.linyi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class CustJDBCSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService UserDetailsService;

    @Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  

        //注入userDetailsService，需要实现userDetailsService接口  
        auth.userDetailsService(UserDetailsService);  
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
