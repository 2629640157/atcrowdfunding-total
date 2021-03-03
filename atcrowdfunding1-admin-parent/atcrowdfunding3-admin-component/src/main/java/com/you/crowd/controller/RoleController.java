package com.you.crowd.controller;

import com.github.pagehelper.PageInfo;
import com.you.crowd.entity.Role;
import com.you.crowd.service.aop.RoleService;
import com.you.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-05  10:07
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    // 使用 @PreAuthorize 注解,为对应的方法设置权限
    @PreAuthorize("hasRole('部长')")
    @ResponseBody
    @RequestMapping("/get/roles.json")
    public ResultEntity<PageInfo<Role>> getRoles(Integer pageNum, Integer pageSize, String keyWord) {

        try {
            PageInfo<Role> pageInfo = roleService.getRolesByKeyWord(pageNum, pageSize, keyWord);
            return ResultEntity.successWithData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed("获取数据失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/input.json")
    public ResultEntity addRole(Role role) {
        try {
            roleService.saveRole(role);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed("插入角色失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public ResultEntity updateRole(Role role) {
        try {
            roleService.updateRole(role);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed("修改角色失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/delete.json")
    public ResultEntity deleteRoles(@RequestBody List<Integer> roleIdList) {
        try {
            roleService.deleteMoreRoles(roleIdList);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed("删除角色失败！");
        }
    }
}
