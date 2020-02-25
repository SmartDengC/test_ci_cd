package com.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserMessageController
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2020/2/24 20:47
 * @Description 返回用户的json字符串
 **/
@RestController
@CrossOrigin
public class UserMessageController {

	@RequestMapping("/usermessage")
	public String userMessage(HttpServletRequest request){
		Object message = request.getAttribute("message");
		return  message.toString();
	}
}
