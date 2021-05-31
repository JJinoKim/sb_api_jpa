package com.js.apitemplate.member.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
@Getter @Setter
public class MemberVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mbrNo;
	
	@Column(unique = true)
	@NotNull
	private String mberId;			// 회원 ID
	@NotBlank
	private String mberName;		// 회원 이름(닉네임)
	@NotNull
	private String mberPassword;	// 비밀번호
	@NotBlank
	private String insertDate;		// 등록날짜
	@NotBlank
	private String modifyDate;		// 수정날짜
	
	public MemberVo(String mberId,String mberName,String mberPassword,String insertDate,String modifyDate ) {
		this.mberId = mberId;
		this.mberName = mberName;
		this.mberPassword = mberPassword;
		this.insertDate = insertDate;
		this.modifyDate = modifyDate;
	}
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="salt_id")
	private SaltVo saltVo;
	
}
