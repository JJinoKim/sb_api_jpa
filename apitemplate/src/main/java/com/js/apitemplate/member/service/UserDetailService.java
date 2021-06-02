package com.js.apitemplate.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.js.apitemplate.member.repository.MemberRepository;
import com.js.apitemplate.member.vo.MemberVo;
import com.js.apitemplate.member.vo.SecurityMemberVo;
import com.js.apitemplate.util.EncodingUtil;

@Service
public class UserDetailService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private EncodingUtil encodingUtil;
	
	@Override
	public UserDetails loadUserByUsername(String mberId) throws UsernameNotFoundException {
		MemberVo memberVo = memberRepository.findByMberId(mberId);
		if(memberVo == null) {
			throw new UsernameNotFoundException("아이디 :"+mberId +" 사용자 존재하지 않음");
		}
		
		return new SecurityMemberVo(memberVo);
	}

}
