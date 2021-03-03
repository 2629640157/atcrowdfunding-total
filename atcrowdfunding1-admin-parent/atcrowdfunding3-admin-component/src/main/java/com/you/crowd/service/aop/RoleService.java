package com.you.crowd.service.aop;

import com.github.pagehelper.PageInfo;
import com.you.crowd.entity.Role;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-05  10:04
 */
public interface RoleService {
    PageInfo<Role> getRolesByKeyWord(Integer pageNum, Integer pageSize, String keyWord);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteMoreRoles(List<Integer> roleIdList);
}
