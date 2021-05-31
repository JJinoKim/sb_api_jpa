package com.js.apitemplate.member.repository;

import org.springframework.data.repository.CrudRepository;

import com.js.apitemplate.member.vo.MemberVo;

public interface MemberRepository extends CrudRepository<MemberVo, Long> {

	public MemberVo findByMberId(String mberId);
	
	
}
