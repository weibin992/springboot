package cn.weibin.springboot.mapper;

import java.util.List;

import cn.weibin.springboot.entity.Permission;
import cn.weibin.springboot.entity.PermissionExample;

public interface PermissionMapper {
    int countByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<String> selectCodeByUserId(Integer userId);
}