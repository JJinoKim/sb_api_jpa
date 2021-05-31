package com.js.apitemplate.main.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "main")
@Getter @Setter
public class MainVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mbrNo;
	private String id;
	private String name;
	private int age;

	@Builder
	public MainVo(String id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	

}
