package com.js.apitemplate.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.js.apitemplate.main.repository.MainRepository;
import com.js.apitemplate.main.vo.MainVo;

@Service
public class MainService {

	@Autowired
	private MainRepository mainRepository;
	
	public List<MainVo> findAll(){
		List<MainVo> mains = new ArrayList<MainVo>();
		mainRepository.findAll().forEach(e -> mains.add(e));
		return mains;
	}
	
	public Optional<MainVo> findByName(String name) {
		Optional<MainVo> result = Optional.ofNullable(mainRepository.findByName(name));
		return result;
	}
}
