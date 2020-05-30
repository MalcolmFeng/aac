package com.inspur.springsecurityoauth_authorizationserver.service;

import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;
import com.inspur.springsecurityoauth_authorizationserver.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Override
    public SysUser getSysUser(String username) {
        return userMapper.findSysUser(username);
    }

    @Override
    public List<SysRole> selectSysRolesByUserId(Long id) {
        return userMapper.selectSysRolesByUserId(id);
    }

}
