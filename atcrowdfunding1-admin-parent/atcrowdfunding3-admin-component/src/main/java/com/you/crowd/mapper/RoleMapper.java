package com.you.crowd.mapper;

import com.you.crowd.entity.Role;
import com.you.crowd.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByKeyWord(String keyWord);

    void deleteByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    List<Role> getAssignRoles(int id);

    List<Role> getUnAssignRoles(int id);


    void deleteAllAdminWithRoleByAdminId(int id);

    void addByAssignRoleId(@Param("id") int id, @Param("assignRoleId") Integer[] assignRoleId);
}