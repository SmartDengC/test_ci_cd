package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.User;
import com.service.UserConfigService;
import com.utils.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	//分别代表三个检验接口的调用结果 , 默认在不输入任何东西的时候点击提交无效
	private int checkusername = 0;
	private int checkinvitecode = 0;
	private int checkemail = 0;

	private HashMap hashMap;
	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	UserConfigService userConfigService;

	@PostMapping("/checkUsername")
	public String checkUsername(String username) throws JsonProcessingException {
		hashMap = new HashMap();
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

	@PostMapping("/checkEmail")
	public String checkEmail(String email) throws JsonProcessingException {
		hashMap = new HashMap();
		if (userConfigService.checkEmail(email) == 0) {
			checkemail = 1;
			hashMap.put("status", checkemail);
			return objectMapper.writeValueAsString(hashMap);
		} else {
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

		//获得加密过后的密码
		password = new BCryptPasswordEncoder().encode(password);

		hashMap = new HashMap();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		System.out.println("转换后的日期是"+date);
		//三个检验都已经通过了校验checkusername!=0 && checkemail!=0 && checkinvitecode!=0
		if(checkusername!=0 && checkemail!=0 && checkinvitecode!=0) {
			//如果成功写入
			int i = userConfigService.addMember(username, password, email, inviteCode, date);
			if (i == 1) {
				hashMap.put("status", 1);
				logger.info(username + "在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "注册了账号");
			}
			if(i==0){
				hashMap.put("status","添加用户失败,请重试");
			}
		} else {
			//如果有验证失败的情况,就返回所有的验证接口返回的状态码
			hashMap.put("checkusername", checkusername);
			hashMap.put("checkemail", checkemail);
			hashMap.put("checkinvitecode", checkinvitecode);
			//返回三个验证接口返回的状态码
			hashMap.put("status", 0);
		}
		checkinvitecode=checkemail=checkusername=0;
		return objectMapper.writeValueAsString(hashMap);
	}

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
		//获得加密过后的密码
		password = new BCryptPasswordEncoder().encode(password);

		hashMap = new HashMap();
		hashMap.put("status",userConfigService.savePwd(email,password));
		logger.info(email+"的用户在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"重置了密码");
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
