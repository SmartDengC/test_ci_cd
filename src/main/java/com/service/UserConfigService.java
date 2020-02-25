package com.service;

import com.pojo.Permission;
import com.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName UserConfigService
 * @Author Dell
 * @Time 2019/11/24 16:29
 * @Description TODO
 **/
public interface UserConfigService {
	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 * 查询当前用户,根据用户名
	 * @Author 唐世杰
	 * @param username
	 * @return com.pojo.User
	 */
	public User findByUsername(String username);

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 * 查询当前用户的权限,根据用户名
	 * @Author 唐世杰
	 * @param username
	 * @return java.util.List<com.pojo.Permission>
	 */
	public List<Permission> findPermissionByUsername(String username);

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 *
	 * @Author 唐世杰
	 * @param username 用户名
	 * @return int
	 */
	public int checkUsername(String username);
	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 *
	 * @Author 唐世杰
	 * @param email 邮箱
	 * @return int
	 */
	public int checkEmail(String email);
	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 *
	 * @Author 唐世杰
	 * @param inviteCode 邀请码
	 * @return int
	 */
	public int checkInviteCode(String inviteCode);

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 * 在成功检验后发送邮件
	 * //1正确,2保存邀请码失败,3邮件发送失败
	 * @Author 唐世杰
	 * @param email 邮箱
	 * @param code 验证码
	 * @return int
	 */
	public int sendCode(String email,int code);

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 * 用来在超级管理员处,给指定邮件发送邀请码
	 * @Author 唐世杰
     * @param inviteCode 邀请码
	 * @return int
	 */
	public int sendInviteCode(int inviteCode);

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 * 查看所有用户
	 * @Author 唐世杰
	 * @param
	 * @return int
	 */
	public List<User> list();

	/**
	 * 添加注册的成员,删除注册时使用的邀请码
	 * 在添加之前应该加上对前三个是否重复的校检,全部返回1的时候 就执行保存用户
	 * @param user 新添加的用户对象
	 * @return 新用户主键id
	 */
	public int addMember(User user,String inviteCode);


	/**
	 * 验证码是否存在且有效接口
	 * @param email 邮箱
	 * @param code 验证码
	 * @return 状态码
	 */
	public int checkCode(@Param("username") String email,@Param("code") String code);

	/**
	 * 密码写入数据库接口,参数都不应该填写输入,而是取出来
	 * @param email 邮箱
	 * @param password 修改后的新的密码
	 * @return 状态码
	 */
	public int savePwd(String email, String password);

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2020/01/14
	 * 查找所有的权限
	 *
	 * @Author 唐世杰
	 * @param
	 * @return List
	 */
	public List<String> findAllPermission();

	/*
	 * Modification User: 唐世杰
	 * Modification Date: 2020/02/21
	 *用来给用户赋于权限
	 *
	 * @Author 唐世杰 
	 * @param id 用户id
	 */
	public int grantUser(int id);

	public int delInviteCode(String invitecode);

	/*
	 * Modification User: 唐世杰
	 * Modification Date: 2020/02/24
	 *查找用户的身份信息
	 *1代表普通用户 2 管理员 3超级管理员
	 * @Author 唐世杰
	 * @param id
	 * @return int
	 */
	public int findIdentity(int id);
}
