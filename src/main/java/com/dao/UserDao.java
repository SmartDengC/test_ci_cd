package com.dao;

import com.pojo.Permission;
import com.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 唐世杰
 * @Date 2019/11/19
 * @Version V1.0
 **/
@Mapper
public interface UserDao {

    /**
     * Modification User: 唐世杰
     * Modification Date: 2019/11/28
     *
     * 查询当前用户,根据用户名
     * @Author 唐世杰
     * @param username
     * @return com.pojo.User
     */
    public User findByUsername(@Param("username") String username);
    /**
     * Modification User: 唐世杰
     * Modification Date: 2019/11/28
     *
     * 查询当前用户的权限,根据用户名
     * @Author 唐世杰
     * @param username
     * @return java.util.List<com.pojo.Permission>
     */
    public List<Permission> findPermissionByUsername(@Param("username") String username);

    /**
     * Modification User: 唐世杰
     * Modification Date: 2019/11/28
     *
     *
     * @Author 唐世杰
     * @param username 用户名
     * @return int
     */
    public int checkUsername(@Param("username") String username);
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
	public int sendCode(@Param("email")String email,@Param("code") int code);

	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 * 用来在超级管理员处,给指定邮件发送邀请码
	 * @Author 唐世杰
	 * @param inviteCode 邀请码
	 * @return int
	 */
	public int sendInviteCode(@Param("inviteCode") int inviteCode);
	/**
	 * Modification User: 唐世杰
	 * Modification Date: 2019/11/28
	 *
	 * 查看所有用户
	 * @Author 唐世杰
	 * @param
	 * @return java.util.List<com.pojo.User>
	 */
	public List<User> list();

    /**
     * 添加注册的成员,删除注册时使用的邀请码
     * 在添加之前应该加上对前三个是否重复的校检,全部返回1的时候 就执行保存用户
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param invitecode 邀请码
     * @param date 用户创建账号的时间
     * @return 状态码
     */
    public int addMember(@Param("username") String username, @Param("password") String password, @Param("email") String email ,@Param("invitecode") String invitecode,@Param("createDate") String date);


    /**
     * 验证码是否存在且有效接口
     * @param email 邮箱
     * @param code 验证码
     * @return 状态码
     */
    public int checkCode(@Param("email") String email , @Param("code") String code);

    /**
     * 密码写入数据库接口,参数都不应该填写输入,而是取出来
     * @param email 邮箱
     * @param password 修改后的新的密码
     * @return 状态码
     */
    public int savePwd(@Param("email") String email , @Param("password") String password);

	public String login(@Param("username") String username);
}
