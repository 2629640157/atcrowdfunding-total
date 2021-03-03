package com.you.crowd.mapper;

import com.you.crowd.entity.Auth;
import com.you.crowd.entity.AuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> getAuthByRoleId(Integer id);

    void deleteAllAuthByRoleId(@Param("id") Integer id);

    void insertAuthByRoleId(@Param("id") Integer id, @Param("authIds") List<Integer> authIds);

    ArrayList<Auth> getAuthByAdminId(Integer id);
}