package com.inspur.springsecurityoauth_authorizationserver.service;


import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;

import java.util.List;
import java.util.Set;

public interface UserService {

    SysUser getSysUser(String username);

    List<SysRole> selectSysRolesByUserId(Long id);

    public Set<String> selectUrlsByUserId(Long userId);

    Set<String> selectPermsByUserId(Long userId);
}
