package com.you.crowd.config;

import com.you.crowd.entity.Admin;
import com.you.crowd.entity.Auth;
import com.you.crowd.entity.Role;
import com.you.crowd.service.aop.AdminService;
import com.you.crowd.service.aop.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-10  16:54
 */
@Component
public class CrowUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {
//        1.根据用户的登录账号查询数据库得到登陆密码
        Admin admin = adminService.getAdminsByLoginAcct(loginAcct);
        String password = admin.getUserPswd();
//        2.需要封装一个authorities 对象 这个对象就是用户对应的角色和权限信息
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        3.获取用户的角色和权限信息
//        4.获取角色信息
        List<Role> roleList = adminService.getAssignRoles(admin.getId());
//        5.获取权限信息
//        ArrayList<Auth> authList= authService.getAuthByAdminId(admin.getId());
        ArrayList<Auth> authList = new ArrayList<>();
        for (Role role : roleList) {
            List<Integer> authIds = authService.getAuthByRoleId(role.getId());
            for (Integer authId : authIds) {
                Auth auth = authService.getAuthByAuthId(authId);
                if (!authList.contains(auth) && auth.getName() != null && !auth.getName().equals("")) {
                    authList.add(auth);
                }
            }
        }
//      6.将角色信息封装到authorities中
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
//                获取角色名
                String roleName = role.getRoleName();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);
                authorities.add(authority);
            }
        }
//        将权限信息封装到authorities中
        if (authList != null && authList.size() > 0) {
            for (Auth auth : authList) {
//                获取权限名
                String name = auth.getName();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(name);
                authorities.add(authority);

            }
        }
//        创建一个类，扩展User类，将原始的admin对象封装好
        CrowdUserDetails userDetails = new CrowdUserDetails(admin, authorities);
        return userDetails;
    }
}
