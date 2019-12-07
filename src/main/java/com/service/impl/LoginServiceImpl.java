package com.service.impl;

import com.dao.UserDao;
import com.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName LoginServiceImpl
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2019/12/8 2:07
 * @Description TODO
 **/
@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	UserDao userDao;

	@Override
	public String login(String username) {
		return userDao.login(username);
	}
}
