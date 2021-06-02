package com.js.apitemplate.member.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.js.apitemplate.member.service.CookieUtil;
import com.js.apitemplate.member.service.JwtUtil;
import com.js.apitemplate.member.service.UserDetailService;
import com.js.apitemplate.member.vo.MemberVo;
import com.js.apitemplate.util.RedisUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {


	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
    private CookieUtil cookieUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final Cookie jwtToken = cookieUtil.getCookie(request, JwtUtil.ACCESS_TOKEN_NAME);

		String username = null;
		String jwt = null;
		String refreshJwt = null;
		String refreshUname = null;

		try {
			if (jwtToken != null) {
				jwt = jwtToken.getValue();
				username = jwtUtil.getUserId(jwt);
			}
			if (username != null) {
				UserDetails userDetails = userDetailService.loadUserByUsername(username);

				if (jwtUtil.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		} catch (ExpiredJwtException e) {
			Cookie refreshToken = cookieUtil.getCookie(request, JwtUtil.REFRESH_TOKEN_NAME);
			if (refreshToken != null) {
				refreshJwt = refreshToken.getValue();
			}
		} catch (Exception e) {

		}

		try {
			if (refreshJwt != null) {
				refreshUname = redisUtil.getData(refreshJwt);

				if (refreshUname.equals(jwtUtil.getUserId(refreshJwt))) {
					UserDetails userDetails = userDetailService.loadUserByUsername(refreshUname);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

					MemberVo member = new MemberVo();
					member.setMberId(refreshUname);
					String newToken = jwtUtil.generateToken(member);

					Cookie newAccessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, newToken);
					response.addCookie(newAccessToken);
				}
			}
		} catch (ExpiredJwtException e) {

		}

		filterChain.doFilter(request, response);
	}

}
