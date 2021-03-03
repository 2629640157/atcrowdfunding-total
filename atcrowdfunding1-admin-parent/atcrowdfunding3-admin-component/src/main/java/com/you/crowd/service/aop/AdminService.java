package com.you.crowd.service.aop;


import com.github.pagehelper.PageInfo;
import com.you.crowd.entity.Admin;
import com.you.crowd.entity.Role;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-06-29  9:19
 */
public interface AdminService {
    List<Admin> getAllAdmins();

    Integer updateAdminById(Admin admin);

    Admin getAdminByLoginAcctAndPswd(String loginAcct, String userPswd);

    PageInfo<Admin> getAdminsByKeyWord(String keyWord, Integer pageNum, Integer pageSize);

    void removeAdminById(int id);

    void addAdmin(Admin admin);

    Admin getAdminsById(int id);

    void updateAdmin(Admin admin, String originalLoginAcct);


    List<Role> getAssignRoles(int id);

    List<Role> getUnAssignRoles(int id);

    void addByAssignRoleId(Integer[] assignRoleId, int id);

    Admin getAdminsByLoginAcct(String loginAcct);
}
