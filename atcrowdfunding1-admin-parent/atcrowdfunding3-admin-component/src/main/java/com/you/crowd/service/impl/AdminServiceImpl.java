package com.you.crowd.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.you.crowd.entity.Admin;
import com.you.crowd.entity.AdminExample;
import com.you.crowd.entity.Role;
import com.you.crowd.mapper.AdminMapper;
import com.you.crowd.mapper.RoleMapper;
import com.you.crowd.service.aop.AdminService;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.exception.LoginAcctAlreadyExist;
import com.you.ssm.exception.LoginFailedException;
import com.you.ssm.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 游斌
 * @create 2020-06-29  9:20
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;

    public List<Admin> getAllAdmins() {
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        return admins;
    }

    public Integer updateAdminById(Admin admin) {
        int i = adminMapper.updateByPrimaryKey(admin);
//        int a = 10 / 0;
        return i;
    }

    public Admin getAdminByLoginAcctAndPswd(String loginAcct, String userPswd) {
//       1. 根据账号查询数据库中的admin信息
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
//        2.判断admin是否存在，如果不存在抛出LoginFailedException异常，如果存在校验密码是否正确。
        if (admins == null || admins.size() < 1) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        Admin admin = admins.get(0);

        if (!Objects.equals(CrowdUtil.md5(userPswd), admin.getUserPswd())) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }

    public PageInfo<Admin> getAdminsByKeyWord(String keyWord, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = adminMapper.selectByKeyWord(keyWord);
        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
        return adminPageInfo;
    }

    @Override
    public void removeAdminById(int id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void addAdmin(Admin admin) {
        List<Admin> admins = validateLoginAcctExist(admin);
        if (admins != null && admins.size() > 0) {
            throw new LoginAcctAlreadyExist(CrowdConstant.MESSAGE_LOGIN_ACCT_EXIST_ADD);
        }
        admin.setUserPswd(CrowdUtil.md5("123456"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String dateStr = dateFormat.format(new Date());
        admin.setCreateTime(dateStr);
        adminMapper.insert(admin);
    }


    @Override
    public Admin getAdminsById(int id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    public void updateAdmin(Admin admin, String originalLoginAcct) {
        //        判断用户名是否被修改
        if (!Objects.equals(admin.getLoginAcct().trim(), originalLoginAcct)) {
            //            修改用户名，先判断是否存在此用户，如果没有则做修改操作，反之
            List<Admin> admins = validateLoginAcctExist(admin);
            if (admins != null && admins.size() > 0) {
                throw new LoginAcctAlreadyExist(CrowdConstant.MESSAGE_LOGIN_ACCT_EXIST_UPDATE);
            }
        }
        //            没有修改用户名直接做修改操作
        adminMapper.updateByPrimaryKey(admin);
    }

    //查询已分配角色
    @Override
    public List<Role> getAssignRoles(int id) {
        return roleMapper.getAssignRoles(id);
    }

    //查询未分配角色
    @Override
    public List<Role> getUnAssignRoles(int id) {
        return roleMapper.getUnAssignRoles(id);
    }

    @Override
    public void addByAssignRoleId(Integer[] assignRoleId, int id) {
        roleMapper.deleteAllAdminWithRoleByAdminId(id);
        roleMapper.addByAssignRoleId(id, assignRoleId);
    }

    @Override
    public Admin getAdminsByLoginAcct(String loginAcct) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins.get(0);

    }

    private List<Admin> validateLoginAcctExist(Admin admin) {
        //        校验用户是否存在
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(admin.getLoginAcct());
        return adminMapper.selectByExample(adminExample);
    }
}
