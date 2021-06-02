package com.js.apitemplate.member.vo;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter @Setter @ToString
public class SecurityMemberVo extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SecurityMemberVo(MemberVo memberVo) {
		
		super(memberVo.getMberId(),"{noop}"+memberVo.getMberPassword(), AuthorityUtils.createAuthorityList(memberVo.getRole().toString()));
	}

}
