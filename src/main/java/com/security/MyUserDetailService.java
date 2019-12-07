package com.security;

import com.dao.UserDao;
import com.pojo.Permission;
import com.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user != null) {
            List<Permission> permissions = userDao.findPermissionByUsername(username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            //把所有的分装到list中  然后赋值给user的authorities属性中
            for (Permission permission : permissions) {
                //把role的字符串添加list中
                GrantedAuthority authority = new SimpleGrantedAuthority(permission.getPermTag());
                authorities.add(authority);
            }
            //把所有的权限赋值给user
            user.setAuthorities(authorities);
        }
        return user;
    }
}
