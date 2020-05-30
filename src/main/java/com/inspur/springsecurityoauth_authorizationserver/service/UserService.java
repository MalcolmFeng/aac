package com.inspur.springsecurityoauth_authorizationserver.service;


import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;

import java.util.List;

public interface UserService {

    SysUser getSysUser(String username);

    List<SysRole> selectSysRolesByUserId(Long id);
}
