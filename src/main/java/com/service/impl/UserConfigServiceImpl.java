package com.service.impl;

import com.dao.UserDao;
import com.pojo.Permission;
import com.pojo.User;
import com.service.UserConfigService;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.SQLException;
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

	@Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	@Override
	public int addMember(User user,String inviteCode) {
		int delinviteCode=0;
		int addmember=0;
		int grantuser=0;
		try {
			int id = user.getId();
			addmember = userDao.addMember(user);
			grantuser = userDao.grantUser(id);
			delinviteCode = userDao.delInviteCode(inviteCode);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		if (addmember!=0&&grantuser!=0&&delinviteCode!=0){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int checkCode(String email, String code) {
		return userDao.checkCode(email,code);
	}

	@Override
	public int savePwd(String email, String password) {
		return userDao.savePwd(email,password);
	}

	@Override
	public List<String> findAllPermission(){return userDao.findAllPermission();}

	@Override
	public int grantUser(int id) {
		return userDao.grantUser(id);
	}

	@Override
	public int delInviteCode(String invitecode) {
		return userDao.delInviteCode(invitecode);
	}

	@Override
	public int findIdentity(int id) {
		return userDao.findIdentity(id);
	}
}
