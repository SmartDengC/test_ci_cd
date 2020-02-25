package com.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author Dell
 * @Date 2019/11/19
 * @Version V1.0
 **/
public class User implements UserDetails {

    //id
    private Integer id;
    //username
    private String username;
    //password
    private String password;
    //邮箱
    private String email;
    //创建日期
    private String createDate;
    //上次登录时间
    private String lastLoginTime;
    //security框架的可用
    private boolean enabled;
    //security框架的账户有效
    private boolean accountNonExpired;
    //security框架的账户未锁
    private boolean accountNonLocked;
    //security框架的凭证未过期
    private boolean credentialsNonExpired;
    //用户所拥有的权限
    private List<GrantedAuthority> authorities = new ArrayList<>();
    //用户身份的信息 例如:1普通用户 2管理员 3超级管理员
	private Integer identity;



	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", createDate='" + createDate + '\'' +
				", lastLoginTime='" + lastLoginTime + '\'' +
				", enabled=" + enabled +
				", accountNonExpired=" + accountNonExpired +
				", accountNonLocked=" + accountNonLocked +
				", credentialsNonExpired=" + credentialsNonExpired +
				", authorities=" + authorities +
				", identity=" + identity +
				'}';
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public User(String username, String password, String email, String createDate, String lastLoginTime, boolean enabled, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, Integer identity) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.createDate = createDate;
		this.lastLoginTime = lastLoginTime;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.authorities = authorities;
		this.identity = identity;
	}

	public User() {
    }
}
