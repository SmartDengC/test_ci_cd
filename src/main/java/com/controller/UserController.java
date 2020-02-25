package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.impl.UserConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * @ClassName UserController
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2020/2/20 14:43
 * @Description 这个Controller用来在登录任意时候使用下面接口来查看当前登录用户的信息
 **/
@RestController
@CrossOrigin
public class UserController {

	private ObjectMapper objectMapper = new ObjectMapper();

	//用户名
	@RequestMapping(value = "/username")
	public String currentUserName(Authentication authentication) {
		return authentication.getName();
	}

	//权限
	@RequestMapping(value = "/authorities")
	public String currentAuthorities(Authentication authentication) {
		return authentication.getAuthorities().toString();
	}

	//用户信息(更大的范围,包括权限、用户名、id等等)
	@RequestMapping(value = "/principal")
	public String currentPrincipal(Authentication authentication) throws JsonProcessingException {
		Object principal = authentication.getPrincipal();
		return objectMapper.writeValueAsString(principal);
	}

	//为了完整性添加的,实际这些详细信息用不到
	@RequestMapping(value = "/details")
	public String currentDetails(Authentication authentication) {
		return authentication.getDetails().toString();
	}

}
