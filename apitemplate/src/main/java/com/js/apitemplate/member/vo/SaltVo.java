package com.js.apitemplate.member.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class SaltVo {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String salt;
	
	public SaltVo() {
		
	}
	
	public SaltVo(String salt) {
		this.salt = salt;
	}
}
