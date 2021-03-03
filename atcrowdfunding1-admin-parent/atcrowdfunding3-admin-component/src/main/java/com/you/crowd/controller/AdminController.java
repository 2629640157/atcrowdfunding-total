package com.you.crowd.controller;

import com.github.pagehelper.PageInfo;
import com.you.crowd.entity.Admin;
import com.you.crowd.entity.Role;
import com.you.crowd.service.aop.AdminService;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 游斌
 * @create 2020-07-01  16:41
 */
@RequestMapping("/admin")
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/do/login.html")
    public String login(String loginAcct, String userPswd, HttpSession session) {
//        1.获取前台表单传入的数据
//        2.根据用户名查询是否存在此用户
        Admin admin = adminService.getAdminByLoginAcctAndPswd(loginAcct, userPswd);
        session.setAttribute(CrowdConstant.ATTR_NAME_ADMIN, admin);
//     为了避免登陆后刷新是表单的重复提交，所以这里用重定向
        return "redirect:/admin/to/main.html";
    }

    @RequestMapping("/do/loginOut.html")
    public String loginOut(HttpSession session) {
        session.invalidate();
//     为了避免登陆后刷新是表单的重复提交，所以这里用重定向
        return "redirect:/admin/to/admin-login.html";
    }

    @RequestMapping("/to/user.html")
    public String toUser(@RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
                         @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                         Map map
    ) {
        //查询数据库拿到数据
        PageInfo<Admin> adminPageInfo = adminService.getAdminsByKeyWord(keyWord, pageNum, pageSize);
        map.put("adminPageInfo", adminPageInfo);
        return "page/user";
    }

    @RequestMapping("/do/remove.html")
    public String removeAdminById(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                  @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
                                  @RequestParam(value = "id") int id) {
        adminService.removeAdminById(id);
        return "redirect:/admin/to/user.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
    }

    @RequestMapping("/do/add.html")
    public String addAdmin(Admin admin) {
        adminService.addAdmin(admin);
        return "redirect:/admin/to/user.html";
    }

    @RequestMapping("/to/update.html")
    public String toUpdateAdminById(int pageNum, String keyWord, int id, Map map) {
        Admin admin = adminService.getAdminsById(id);
        map.put(CrowdConstant.ATTR_NAME_ADMIN, admin);
        map.put("pageNum", pageNum);
        map.put("keyWord", keyWord);
        return "page/update";
    }

    @RequestMapping("/do/update.html")
    public String updateAdminById(int pageNum, String keyWord, Admin admin, String originalLoginAcct) {
        adminService.updateAdmin(admin, originalLoginAcct);
        return "redirect:/admin/to/user.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
    }

    @ModelAttribute
    public void getAdminById(@RequestParam(value = "id", required = false) Integer id, Map map) {
        if (id != null) {
            Admin admin = adminService.getAdminsById(id);
            map.put(CrowdConstant.ATTR_NAME_ADMIN, admin);
        }

    }


    //    去分配页面
    @RequestMapping("/to/assign.html")
    public String toAssign(int pageNum, String keyWord, int id, Map map) {
        //根据用户id查询角色分配的信息，并将其放入模型中
        List<Role> AssignRoles = adminService.getAssignRoles(id);
        List<Role> UnAssignRoles = adminService.getUnAssignRoles(id);
        map.put("AssignRoles", AssignRoles);
        map.put("UnAssignRoles", UnAssignRoles);
        map.put("pageNum", pageNum);
        map.put("keyWord", keyWord);
        map.put("id", id);
        return "page/assignRole";
    }

    //用户角色分配
    @RequestMapping("/do/assign.html")
    public String doAssign(int pageNum, String keyWord, int id, Integer[] assignRoleId) {
        if (assignRoleId != null && assignRoleId.length > 0) {
            adminService.addByAssignRoleId(assignRoleId, id);
        }
        return "redirect:/admin/to/user.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
    }
}
