package com.js.apitemplate.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.js.apitemplate.member.service.IMemberService;
import com.js.apitemplate.member.vo.MemberVo;
import com.js.apitemplate.util.DateUtil;
import com.js.apitemplate.util.ServiceResult;

@RestController
public class MemberController {

	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private DateUtil dateUtil;
	
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
}
