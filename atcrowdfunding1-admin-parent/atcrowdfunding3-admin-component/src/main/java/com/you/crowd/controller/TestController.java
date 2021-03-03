package com.you.crowd.controller;

import com.you.crowd.entity.Admin;
import com.you.crowd.service.aop.AdminService;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.exception.LoginFailedException;
import com.you.ssm.util.CrowdUtil;
import com.you.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-01  16:41
 */
@RequestMapping("/test")
@Controller
public class TestController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/test.html")
    public String test1() {
//        int c=10/0;
        for (int i = 0; i < 5; i++) {
            if (i > 3) {
                throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
            }
        }
        return "page/main";
    }

    @ResponseBody
    @RequestMapping("/json.json")
    public List<Admin> jsonTest() {
        return adminService.getAllAdmins();
    }

    @ResponseBody
    @RequestMapping("/jsonWithData1.json")
    public String jsonWithData1(@RequestParam("empIdList[]") List<Integer> ids) {
        for (Integer id : ids) {
            System.out.println("id = " + id);
        }
        return "page/main";

    }

    //    用@RequestBody 注解获取json数据
   /* @ResponseBody
    @RequestMapping("/jsonWithData2.json")
    public String jsonWithData2(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            System.out.println("id = " + id);
        }
        return "page/main";
    }*/
    // 利用创建的ResultEntity统一返回数据格式
    @ResponseBody
    @RequestMapping("/jsonWithData2.json")
    public ResultEntity<String> jsonWithData2(@RequestBody List<Integer> ids, HttpServletRequest request) {
        boolean b = CrowdUtil.judgeRequestType(request);
        System.out.println("b = " + b);

        for (Integer id : ids) {
            System.out.println("id = " + id);
        }
        for (int i = 0; i < 5; i++) {
            if (i > 3) {
                throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
            }
        }
        ResultEntity<String> entity = new ResultEntity<String>("success", null, "page/main");
        return entity;
    }
}
