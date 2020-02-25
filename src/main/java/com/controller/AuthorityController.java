package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @ClassName AuthorityController
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2020/2/21 12:19
 * @Description 用来赋予权限操作接口
 **/
@RestController
@CrossOrigin
public class AuthorityController {

	private HashMap hashMap;
	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	UserConfigService userConfigService;

	//普通用户权限赋予接口,注册时赋予用户权限
	@PostMapping("/grantUser")
	public String user(int id) throws JsonProcessingException {
		hashMap = new HashMap();

		//调用sql语句 在用户与角色之间关联起来 更新user_role表
		int result = userConfigService.grantUser(id);
		if(result!=0){
			hashMap.put("status",1);
		}else{
			hashMap.put("status",0);
		}
		return objectMapper.writeValueAsString(hashMap);
	}

	//管理员权限赋予接口,超管拥有权限,能修改单个用户的单个权限
	@PostMapping("/grantAdmin")
	public void administrator(){

	}

	//超管权限赋予接口,超管拥有权限,能赋予管理员职位和超管职位,这一部分需要单独验证一个密码
	@PostMapping("/grantSuperAdmin")
	public void  superAdministrator(){

	}
}
