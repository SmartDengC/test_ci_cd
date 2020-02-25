package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.User;
import com.service.UserConfigService;
import com.utils.EmailUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @ClassName UserConfigController
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2019/11/24 16:27
 * @Description TODO
 **/
@RestController
@RequestMapping("/userConfig")
public class UserConfigController {

	//日志
	private static final Logger logger = LoggerFactory.getLogger(UserConfigController.class);

	//分别代表4个检验接口的调用结果 , 默认在不输入任何东西的时候点击提交无效
	private int checkusername = 0;
	private int checkinvitecode = 0;
	private int checkemail = 0;
	private int checkPassword = 0;

	private HashMap hashMap;
	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	UserConfigService userConfigService;

	//检查密码是否过长或为空
	@PostMapping("/checkPassword")
	public String checkPassword(String password) throws JsonProcessingException {
		hashMap = new HashMap();

		//判断密码是否为空或过长需要符合规范
		//限制为0~16个字符之间
		if(password.isEmpty()||password.length()>16){
			checkPassword = 0;
			hashMap.put("message","用户名超过16字符或为空");
			return objectMapper.writeValueAsString(hashMap);
		}else{
			checkPassword = 1;
			hashMap.put("message","密码可用");
			return objectMapper.writeValueAsString(hashMap);
		}

	}


	//检查用户名是否重复
	@PostMapping("/checkUsername")
	public String checkUsername(String username) throws JsonProcessingException {
		hashMap = new HashMap();

		//判断用户名是否为空或过长需要符合规范
		//限制为0~16个字符之间
		if(username.isEmpty()||username.length()>16){
			hashMap.put("message","用户名超过16字符或为空");
			return objectMapper.writeValueAsString(hashMap);
		}

		if (userConfigService.checkUsername(username) == 0) {
			checkusername = 1;
			hashMap.put("status", checkusername);
			return objectMapper.writeValueAsString(hashMap);
		} else {
			checkusername = 0;
			hashMap.put("status", checkusername);
			return objectMapper.writeValueAsString(hashMap);
		}
	}

	//检查邀请码是否使用
	@PostMapping("/checkInviteCode")
	public String checkInviteCode(String inviteCode) throws JsonProcessingException {
		hashMap = new HashMap();
		if (userConfigService.checkInviteCode(inviteCode) != 0) {
			checkinvitecode = 1;
			hashMap.put("status", checkinvitecode);
			return objectMapper.writeValueAsString(hashMap);
		} else {
			checkinvitecode = 0;
			hashMap.put("status", checkinvitecode);
			return objectMapper.writeValueAsString(hashMap);
		}
	}

	//检查邮箱是否重复
	@PostMapping("/checkEmail")
	public String checkEmail(String email) throws JsonProcessingException {
		hashMap = new HashMap();

		//是否含有@
		if(email.contains("@"))
		{
			//@不是开头，只有一个@
			if (email.indexOf("@") != 0 &&email.indexOf("@") ==email.lastIndexOf("@") )
			{
				//截取@开头的字符串
				String prefix = email.substring(email.indexOf("@"));
				//含有.的字符
				if(prefix.contains("."))
				{
					//@跟.不靠在一起，并且不以.结尾
					if(prefix.indexOf(".")!=1&&!prefix.endsWith("."))
					{}
					else
					{
						hashMap.put("message","邮箱为空或格式错误");
						return objectMapper.writeValueAsString(hashMap);
					}
				}
				else
				{
					hashMap.put("message","邮箱为空或格式错误");
					return objectMapper.writeValueAsString(hashMap);
				}
			}
			else
			{
				hashMap.put("message","邮箱为空或格式错误");
				return objectMapper.writeValueAsString(hashMap);
			}

		}
		else
		{
			hashMap.put("message","邮箱为空或格式错误");
			return objectMapper.writeValueAsString(hashMap);
		}

		//查重邮箱 邮箱数量为0 说明不重复  返回1代表成功
		if (userConfigService.checkEmail(email) == 0) {
			checkemail = 1;
			hashMap.put("status", checkemail);
			return objectMapper.writeValueAsString(hashMap);
		}
		else {
			checkemail = 0;
			hashMap.put("status", checkemail);
			return objectMapper.writeValueAsString(hashMap);
		}
	}

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/29
	 *
	 * 修改密码的存储内容,应该存储加密过后的密码
	 * @Author 唐世杰
	 * @param username
	 * @param password
	 * @param email
	 * @param inviteCode
	 * @return java.lang.String
	 */
	@PostMapping("/addMember")
	public String addMemeber(String username, String password,String email,String inviteCode) throws JsonProcessingException {
		//封装前必要的准备
		password = new BCryptPasswordEncoder().encode(password);
		hashMap = new HashMap();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		//封装User对象
		User user = new User(username,password,email,date,null,true,true,true,true,1);

		//三个检验都已经通过了校验checkusername!=0 && checkemail!=0 && checkinvitecode!=0
		if(checkusername!=0 && checkemail!=0 && checkinvitecode!=0 && checkPassword!=0) {
			//添加用户
			if(userConfigService.addMember(user, inviteCode)!=0){
				hashMap.put("status",1);
			}else{
				hashMap.put("message","添加失败,数据库繁忙");
			}
		} else {
			//如果有验证失败的情况,就返回所有的验证接口返回的状态码
			hashMap.put("checkusername", checkusername);
			hashMap.put("checkemail", checkemail);
			hashMap.put("checkinvitecode", checkinvitecode);
			hashMap.put("checkPassword", checkPassword);
			//返回三个验证接口返回的状态码
			hashMap.put("status", 0);
		}
		logger.info(username + "在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "注册了账号");
		checkinvitecode=checkemail=checkusername=0;
		return objectMapper.writeValueAsString(hashMap);
	}

	//发送验证码
	@PostMapping("/sendCode")
	public String sendCode(String email) throws JsonProcessingException {

		int i=0;
		//检验邮箱是否有效
		int checkEmail = userConfigService.checkEmail(email);
		if (checkEmail!=1){
			hashMap.put("status",4);//验证邮箱失败
			return objectMapper.writeValueAsString(hashMap);
		}
		//尝试发送邮件,并保存验证码到数据库
		hashMap = new HashMap();
		int code=(int)(Math.random()*9000)+1000;//随机生成四个数字
		i = userConfigService.sendCode(email,code);//保存验证码至数据库
		if(i==1) {
			boolean b = EmailUtils.sendMail(email,1, String.valueOf(code));
			if(b){
				hashMap.put("status",i);
				logger.info(email+"的用户在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"提交了验证码申请");
			}else
				hashMap.put("status",2);//2保存邀请码失败
		}else
			hashMap.put("status",3);//3邮件发送失败

		return objectMapper.writeValueAsString(hashMap);
	}

	//下面这个请求的mapper层没有在等待一定时间后删除的触发器
	@PostMapping("/checkCode")
	public String checkCode(String email,String code) throws JsonProcessingException {
		hashMap = new HashMap();
		hashMap.put("status",userConfigService.checkCode(email, code));
		return objectMapper.writeValueAsString(hashMap);
	}


	@PostMapping("/savePwd")
	public String savePwd(String email, String password) throws JsonProcessingException {

		hashMap = new HashMap();
		//判断密码是否为空或过长需要符合规范
		//限制为0~16个字符之间
		if(password.isEmpty()||password.length()>16){
			checkPassword = 0;
			hashMap.put("message","用户名超过16字符或为空");

		}else{
			checkPassword = 1;
			//获得加密过后的密码
			password = new BCryptPasswordEncoder().encode(password);
			hashMap.put("status",userConfigService.savePwd(email,password));
			logger.info(email+"的用户在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"重置了密码");

		}

		//返回响应
		return objectMapper.writeValueAsString(hashMap);
	}


	@PostMapping("/sendInviteCode")
	public String sendInviteCode(String email) throws JsonProcessingException {

		//尝试发送邮件,并保存邀请码到数据库
		hashMap = new HashMap();
		int inviteCode=(int)(Math.random()*9000)+1000;//随机生成四个数字
		int i = userConfigService.sendInviteCode(inviteCode);//保存邀请码码至数据库
		if(i==1){
			if (EmailUtils.sendMail(email,2, String.valueOf(inviteCode)))
				hashMap.put("status",i);
			else
				hashMap.put("status",3);
		}else{
			hashMap.put("status",2);
		}
		return objectMapper.writeValueAsString(hashMap);
	}

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 * 返回所有用户的集合json字符串,若果没有user就返回status:null
	 * @Author 唐世杰
	 * @param
	 * @return java.lang.String
	 */
	@RequestMapping("/list")
	public String list() throws JsonProcessingException {
		List<User> list = userConfigService.list();
		if (!list.isEmpty()) {
			return objectMapper.writeValueAsString(list);
		}else{
			hashMap.put("status","null");
			return objectMapper.writeValueAsString(hashMap);
		}
	}
}
