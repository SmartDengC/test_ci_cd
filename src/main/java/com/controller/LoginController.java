package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @ClassName LoginController
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2019/12/8 1:51
 * @Description TODO
 **/

@Controller
@ResponseBody
public class LoginController {

	@Autowired
	LoginService loginService;

	HashMap hashMap;
	ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/login")
	public String login(String username,String password) throws JsonProcessingException {
		String login = loginService.login(username);
		boolean check = new BCryptPasswordEncoder().matches(password,login);

		System.out.println(check);
		hashMap=new HashMap();
		if(check){
			hashMap.put("status",1);
		}else{
			hashMap.put("status",0);
		}

		return objectMapper.writeValueAsString(hashMap);
	}


}
