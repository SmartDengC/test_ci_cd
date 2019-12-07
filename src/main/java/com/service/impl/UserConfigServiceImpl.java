package com.service.impl;

import com.dao.UserDao;
import com.pojo.Permission;
import com.pojo.User;
import com.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserConfigServiceImpl
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2019/11/24 16:31
 * @Description TODO
 **/
@Service
public class UserConfigServiceImpl implements UserConfigService{

	@Autowired
	UserDao userDao;

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public List<Permission> findPermissionByUsername(String username) {
		return userDao.findPermissionByUsername(username);
	}

	@Override
	public int checkUsername(String username) {
		return userDao.checkUsername(username);
	}

	@Override
	public int checkEmail(String email) {
		return userDao.checkEmail(email);
	}

	@Override
	public int checkInviteCode(String inviteCode) {
		return userDao.checkInviteCode(inviteCode);
	}

	@Override
	public int sendCode(String email,int code) {
		return userDao.sendCode(email,code);
	}

	@Override
	public int sendInviteCode(int inviteCode) {
		return userDao.sendInviteCode(inviteCode);
	}

	@Override
	public List<User> list() {
		return userDao.list();
	}

	@Override
	public int addMember(String username, String password, String email, String inviteCode, String date) {
		return userDao.addMember(username,password,email,inviteCode,date);
	}

	@Override
	public int checkCode(String email, String code) {
		return userDao.checkCode(email,code);
	}

	@Override
	public int savePwd(String email, String password) {
		return userDao.savePwd(email,password);
	}
}
