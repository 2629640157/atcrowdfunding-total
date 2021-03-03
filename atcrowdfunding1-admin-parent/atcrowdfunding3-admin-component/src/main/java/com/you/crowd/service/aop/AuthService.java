package com.you.crowd.service.aop;

import com.you.crowd.entity.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 游斌
 * @create 2020-07-08  19:18
 */
public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAuthByRoleId(Integer id);

    void updateAuth(Map<String, List<Integer>> map);

    Auth getAuthByAuthId(Integer authId);

    ArrayList<Auth> getAuthByAdminId(Integer id);
}
