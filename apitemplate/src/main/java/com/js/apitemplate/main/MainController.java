package com.js.apitemplate.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	
	
	@PostMapping(path = "/main")
	public Map<String, Object> main( HttpServletRequest request){	
		Map<String,Object> result = new HashMap<String, Object>();
		int a = 3;
		String asdf = (String) request.getAttribute("test");
		String ddd = request.getParameter("test");
		result.put("test",a );
		
		
		return result;
	}
	
}
