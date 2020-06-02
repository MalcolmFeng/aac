package com.inspur.springsecurityoauth_authorizationserver.service;

import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;
import com.inspur.springsecurityoauth_authorizationserver.mapper.SysMenuMapper;
import com.inspur.springsecurityoauth_authorizationserver.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public SysUser getSysUser(String username) {
        return userMapper.findSysUser(username);
    }

    @Override
    public List<SysRole> selectSysRolesByUserId(Long id) {
        return userMapper.selectSysRolesByUserId(id);
    }

    @Override
    public Set<String> selectUrlsByUserId(Long userId){
        List<String> perms = menuMapper.selectUrlsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (perm != null && !perm.equals("")) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (perm != null && !perm.equals("")) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }
}
