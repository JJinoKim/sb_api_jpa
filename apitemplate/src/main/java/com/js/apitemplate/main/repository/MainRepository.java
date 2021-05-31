package com.js.apitemplate.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.js.apitemplate.main.vo.MainVo;

@Repository
public interface MainRepository extends JpaRepository<MainVo, Long> {

	public List<MainVo> findById(String id);

	public MainVo findByName(String name); // like검색도 가능 
	
	
	public List<MainVo> findByNameLike(String keyword);

}
