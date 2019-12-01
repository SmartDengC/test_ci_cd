package com.dao;

import com.pojo.Permission;
import com.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @ClassName UseCodeDao
 * @Author 何政梁
 * @Time 2019/11/21 11:19
 * @Description 用户模块配置的接口，查询权限、查询用户信息、保存数据、保存邀请码等等
 */
public interface UserCodeDao {

    /**
     * 调用接口查询用户
     * @param username 注册的用户名
     * @return 返回user信息
     *  测试成功
     */
    public User FindByUsername(String username);


    /**
     * 调用接口查询用户权限
     * @param username 注册的用户名
     * @return 返回一个权限的list封装
     */
    public List<Permission> FindPermissionByUsername(@Param("username") String username);

    /**
     * 调用的接口检测用户名、邮箱、邀请码是否唯一
     * @param username 注册的用户名
     * @param useremail 邮箱
     * @param invitecode 邀请码
     * @return true or false
     * 测试成功
     */
    public boolean UserRegister(@Param("username") String username,@Param("email") String useremail,@Param("invitecode") String invitecode);


    /**
     * 注册成功写入数据库接口,invite_code用来把状态码变为0(不可用)
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param invitecode 邀请码
     * @param date 用户创建账号的时间
     * @return 状态码
     * 测试成功
     */
    public int SaveUserInformation(@Param("username") String username, @Param("password") String password, @Param("email") String email ,@Param("createDate") String date,@Param("invitecode") String invitecode);


    //------------------有点问题,这个界面应该是用户管理的界面,用户能登录进来那么就有他自己的修改权限
    /**
     * 检验账号\邮箱是否存在且相绑定接口(修改密码),用户名从request里面取,
     * @param username 用户名 ,应该从request里面取 ,不应该让用户输入
     * @param password 用来检测是不是账号主人
     * @param email 用来获取验证码
     * @return 状态码
     * 测试成功
     */
    public int CheckAccountChange(@Param("username") String username,@Param("password") String password,@Param("email") String email);

    /**
     * 检验账号\邮箱是否存在且相绑定接口(修改密码)
     * @param username 让用户输入的用户名
     * @param email 用来获取验证码
     * @return 状态码
     * 测试成功
     */
    public int CheckAccountFind(@Param("username") String username,@Param("email") String email);

    /**
     * 验证码是否存在且有效接口
     * @param username 用户名
     * @param code 验证码
     * @return 状态码
     * 测试成功
     */
    public int CheckCode(@Param("username") String username , @Param("code") String code);

    /**
     * 密码写入数据库接口,参数都不应该填写输入,而是取出来
     * @param username 用户名
     * @param password 修改后的新的密码
     * @return 状态码
     * 成功
     */
    public int UpdatePassword(@Param("username") String username , @Param("password") String password);
}