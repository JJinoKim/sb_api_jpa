package com.js.apitemplate.member.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	/**
	 * 인증을 위한 provider
	 */
	@Override
    protected void configure(AuthenticationManagerBuilder auth) {
      
    }
    
	/**
	 * resource에 대한 보안설정
	 */
    @Override
    public void configure(WebSecurity web) {
	}

    /**
     * HTTP 보안설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.authorizeRequests()
    			.antMatchers("/main","/signup","/login").permitAll() 		// 인증 없어도 통과
    			.antMatchers("/**/**").authenticated()	// 인증 되어야만 함
    			.and()
    		.csrf()
    			.disable();
	}
}
