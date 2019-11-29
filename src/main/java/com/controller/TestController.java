package com.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName TestController
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2019/11/28 19:12
 * @Description TODO
 **/
public class TestController {
	public static void main(String[] args) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		int num=(int)(Math.random()*9000)+1000;
		System.out.println(String.valueOf(num));
		String password = "123456";
		System.out.println(new BCryptPasswordEncoder().encode(password));
	}
}
