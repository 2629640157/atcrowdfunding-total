package com.you.crowd.controller;

import com.you.crowd.entity.Auth;
import com.you.crowd.entity.Role;
import com.you.crowd.service.aop.AuthService;
import com.you.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 游斌
 * @create 2020-07-08  19:16
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("do/getAll.json")
    public ResultEntity<List<Auth>> getAllAuth() {
        try {
//            获取所有权限信息
            List<Auth> authList = authService.getAllAuth();
            return ResultEntity.successWithData(authList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/do/getAuthByRoleId.json")
    public ResultEntity<List<Integer>> getAuthByRoleId(Integer id) {
        try {
//            获取所有权限信息
            List<Integer> authIds = authService.getAuthByRoleId(id);
            return ResultEntity.successWithData(authIds);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/do/updateAuth.json")
    public ResultEntity updateAuth(@RequestBody Map<String, List<Integer>> map) {
        try {
//            获取所有权限信息
            authService.updateAuth(map);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
