package com.js.apitemplate.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.js.apitemplate.main.repository.MainRepository;
import com.js.apitemplate.main.service.MainService;
import com.js.apitemplate.main.vo.MainVo;

@RestController
public class MainController {

	@Autowired
	MainService mainService;
	
	
	@PostMapping(path = "/main")
	public ResponseEntity<List<MainVo>> main( HttpServletRequest request){	
		Map<String,Object> result = new HashMap<String, Object>();
		int a = 3;
		String asdf = (String) request.getAttribute("test");
		String ddd = request.getParameter("test");
		List<MainVo> reuslt = mainService.findAll();
		result.put("test",reuslt );
		
		
		return new ResponseEntity<List<MainVo>>(reuslt,HttpStatus.OK);
	}
	
	@PostMapping(path = "/find/{name}")
	public ResponseEntity<MainVo> find( HttpServletRequest request, @RequestBody MainVo vo, @PathVariable("name") String name){
		

		Map<String,Object> result = new HashMap<String, Object>();
		int a = 3;
		String asdf = vo.getName();
		Optional<MainVo> reuslt = mainService.findByName(name);
		result.put("reuslt",reuslt );
		
		
		return new ResponseEntity<MainVo>(reuslt.get() ,HttpStatus.OK);
	}
	
}
