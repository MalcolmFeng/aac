package com.inspur.springsecurityoauth_authorizationserver.service;

import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义UserDetailsService
 * @Author Malcolm
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 重写loadUserByUsername，从数据库中查询用户信息和角色权限；
     *
     * 用户登陆时，执行此方法；
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = userService.getSysUser(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }

        List<SysRole> roles = userService.selectSysRolesByUserId(user.getUserId());

        return new org.springframework.security.core.userdetails.User(
                user.getLoginName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getGrantedAuthorities(roles)
        );
    }


    private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<SysRole> roles) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (SysRole role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleKey()));
        }
        return authorities;
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
