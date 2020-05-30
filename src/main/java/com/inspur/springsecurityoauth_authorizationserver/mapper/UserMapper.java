package com.inspur.springsecurityoauth_authorizationserver.mapper;

import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    SysUser findSysUser(String username);

    List<SysRole> selectSysRolesByUserId(Long id);
}
