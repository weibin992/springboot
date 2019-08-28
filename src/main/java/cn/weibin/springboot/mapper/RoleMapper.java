package cn.weibin.springboot.mapper;

import java.util.List;

import cn.weibin.springboot.entity.Role;
import cn.weibin.springboot.entity.RoleExample;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<String> selectCodeByUserId(Integer userId);
}