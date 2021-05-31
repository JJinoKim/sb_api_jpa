package com.js.apitemplate.member.service;

import com.js.apitemplate.member.vo.MemberVo;
import com.js.apitemplate.util.ServiceResult;

public interface IMemberService {
	
	/**
	 * 회원가입	
	 * @param memberVo
	 */
	public ServiceResult signUpMember(MemberVo memberVo)throws Exception ;
	
	/**
	 * 로그인
	 * @param mberId
	 * @return
	 */
	public MemberVo loginUser(String mberId, String mberPassword)throws Exception ;
}
