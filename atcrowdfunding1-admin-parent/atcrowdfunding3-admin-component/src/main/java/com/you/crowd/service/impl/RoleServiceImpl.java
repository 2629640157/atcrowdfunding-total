package com.you.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.you.crowd.entity.Role;
import com.you.crowd.entity.RoleExample;
import com.you.crowd.mapper.RoleMapper;
import com.you.crowd.service.aop.RoleService;
import com.you.ssm.exception.LoginFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-05  10:06
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getRolesByKeyWord(Integer pageNum, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleMapper.selectByKeyWord(keyWord);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return pageInfo;
    }

    @Override
    public void saveRole(Role role) {
//        验证角色名是否存在
        /*RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andRoleNameEqualTo(role.getRoleName());
        List<Role> roles = roleMapper.selectByExample(roleExample);
//        存在
        if (roles != null && roles.size() > 0) {
            throw new LoginFailedException("");
        }*/
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void deleteMoreRoles(List<Integer> roleIdList) {
        roleMapper.deleteByRoleIdList(roleIdList);
    }
}
