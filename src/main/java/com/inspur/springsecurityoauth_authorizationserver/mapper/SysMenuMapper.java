package com.inspur.springsecurityoauth_authorizationserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 菜单表 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysMenuMapper {
    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectUrlsByUserId(Long userId);

    List<String> selectPermsByUserId(Long userId);
}
