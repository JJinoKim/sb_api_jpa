package com.js.apitemplate.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.js.apitemplate.member.service.CookieUtil;
import com.js.apitemplate.member.service.IMemberService;
import com.js.apitemplate.member.service.JwtUtil;
import com.js.apitemplate.member.vo.MemberVo;
import com.js.apitemplate.util.DateUtil;
import com.js.apitemplate.util.RedisUtil;
import com.js.apitemplate.util.ServiceResult;

@RestController
public class MemberController {

	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private DateUtil dateUtil;
	@Autowired
    private CookieUtil cookieUtil;
	
	@Autowired
    private JwtUtil jwtUtil;
	@Autowired
    private RedisUtil redisUtil;
	
	@PostMapping(path = "/signup")
	public ResponseEntity<ServiceResult> signup(
			  HttpServletRequest request
			, @RequestBody MemberVo memberVo) throws Exception{
		
		String nowDate = dateUtil.nowDate("yyyy-MM-dd HH:mm:ss");
		memberVo.setInsertDate(nowDate);
		memberVo.setModifyDate(nowDate);
		ServiceResult result = memberService.signUpMember(memberVo);
		
		return new ResponseEntity<ServiceResult>(result,HttpStatus.OK);
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<MemberVo> login(
			  HttpServletRequest request
			, HttpServletResponse response
			, @RequestBody MemberVo memberVo) throws Exception{
		
		String nowDate = dateUtil.nowDate("yyyy-MM-dd HH:mm:ss");
		memberVo.setInsertDate(nowDate);
		memberVo.setModifyDate(nowDate);
		MemberVo result = memberService.loginUser(memberVo.getMberId(), memberVo.getMberPassword());
		String token = jwtUtil.generateToken(memberVo);
		String refreshJwt = jwtUtil.generateRefreshToken(memberVo);
		Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
//        redisUtil.setDataExpire(refreshJwt, memberVo.getMberId(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
        response.addCookie(accessToken);
        response.addCookie(refreshToken);
		
		return new ResponseEntity<MemberVo>(result,HttpStatus.OK);
	}
	
}
