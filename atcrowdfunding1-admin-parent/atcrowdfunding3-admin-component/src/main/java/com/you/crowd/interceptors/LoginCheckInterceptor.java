package com.you.crowd.interceptors;

import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 游斌
 * @create 2020-07-02  20:30
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进行登陆检查  检查session域对象中是否存在admin属性对应的值
        HttpSession session = request.getSession();
        //获取session域的admin属性
        Object admin = session.getAttribute(CrowdConstant.ATTR_NAME_ADMIN);
        if (admin == null) {
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }
        return true;
    }
}
