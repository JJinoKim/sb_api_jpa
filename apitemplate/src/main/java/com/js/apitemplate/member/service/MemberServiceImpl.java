package com.js.apitemplate.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.js.apitemplate.member.repository.MemberRepository;
import com.js.apitemplate.member.vo.MemberVo;
import com.js.apitemplate.member.vo.SaltVo;
import com.js.apitemplate.util.EncodingUtil;
import com.js.apitemplate.util.ServiceResult;

@Service
public class MemberServiceImpl implements IMemberService{

	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private EncodingUtil encodingUtil;
	
	@Override
	public ServiceResult signUpMember(MemberVo memberVo){
		try {
			String password = memberVo.getMberPassword();
			String salt = encodingUtil.genSalt();
			memberVo.setSaltVo(new SaltVo(salt));
			memberVo.setMberPassword(encodingUtil.encodePassword(salt, password));
			memberRepository.save(memberVo);
			return ServiceResult.SUCCESS;
		}catch (Exception e) {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public MemberVo loginUser(String mberId, String mberPassword) throws Exception {
		MemberVo memberVo = memberRepository.findByMberId(mberId);
		if(memberVo == null) {
			throw new Exception("not exist member");
		}
		String salt = memberVo.getSaltVo().getSalt();
		mberPassword = encodingUtil.encodePassword(salt, mberPassword);
		if(!memberVo.getMberPassword().equals(mberPassword)) {
			throw new Exception("wrong password");
		}
		return memberVo;
	}

}
