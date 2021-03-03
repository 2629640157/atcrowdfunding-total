package com.you.crowd.service.impl;

import com.you.crowd.entity.Auth;
import com.you.crowd.mapper.AuthMapper;
import com.you.crowd.service.aop.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 游斌
 * @create 2020-07-08  19:19
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(null);
    }

    @Override
    public List<Integer> getAuthByRoleId(Integer id) {
        return authMapper.getAuthByRoleId(id);
    }

    @Override
    public void updateAuth(Map<String, List<Integer>> map) {
        authMapper.deleteAllAuthByRoleId(map.get("id").get(0));
        if (map.get("authIds") != null && map.get("authIds").size() > 0) {
            authMapper.insertAuthByRoleId(map.get("id").get(0), map.get("authIds"));
        }
    }

    @Override
    public Auth getAuthByAuthId(Integer authId) {
        return authMapper.selectByPrimaryKey(authId);
    }

    @Override
    public ArrayList<Auth> getAuthByAdminId(Integer id) {
        return authMapper.getAuthByAdminId(id);
    }
}
