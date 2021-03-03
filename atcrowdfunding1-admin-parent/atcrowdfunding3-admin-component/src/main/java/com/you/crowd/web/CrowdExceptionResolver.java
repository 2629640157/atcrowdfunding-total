package com.you.crowd.web;

import com.google.gson.Gson;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.exception.AccessForbiddenException;
import com.you.ssm.exception.LoginAcctAlreadyExist;
import com.you.ssm.exception.LoginFailedException;
import com.you.ssm.util.CrowdUtil;
import com.you.ssm.util.ResultEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 游斌
 * @create 2020-07-02  10:24
 */
//目标受理请求发生异常时候该类进行解析
@ControllerAdvice
public class CrowdExceptionResolver {
    @ExceptionHandler(LoginAcctAlreadyExist.class)
    public ModelAndView handlerDuplicateKey(HttpServletRequest request, HttpServletResponse response, LoginAcctAlreadyExist exception) throws IOException {
        String viewName = "";
        if (exception.getMessage().contains("修改")) {
            viewName = "page/update";
        } else {
            viewName = "page/add";
        }
        ModelAndView modelAndView = commentExceptionResolver(request, response, exception, viewName);
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        String viewName = "errors/system-error";
        ModelAndView modelAndView = commentExceptionResolver(request, response, exception, viewName);
        return modelAndView;
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ModelAndView handlerAccessForbidden(HttpServletRequest request, HttpServletResponse response, AccessForbiddenException exception) throws IOException {
        String viewName = "page/admin-login";
        ModelAndView modelAndView = commentExceptionResolver(request, response, exception, viewName);
        return modelAndView;
    }

    //    提取一个公共的异常处理方法
    private ModelAndView commentExceptionResolver(HttpServletRequest request, HttpServletResponse response, Exception exception, String viewName) {
//        判断请求的类型是什么类型
        boolean b = CrowdUtil.judgeRequestType(request);
        if (b) {
//           如果是一个Ajax请求i，获取异常消息，存入到ResultEntity中，并转换成json串，给出响应
//            获取异常信息
            String message = exception.getMessage();
//            创建一个ResultEntity对象
            ResultEntity<Object> failed = ResultEntity.failed(message);
//            使用Gson进行解析ResultEntity对象拿到json串，写入响应中
//            创建Gson的对象
            Gson gson = new Gson();
//            使用gson对象转换ResultEntity
            String jsonStr = gson.toJson(failed);
//            通过Response对象将jsonStr写入响应中
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.write(jsonStr);
//            设置响应类型
            response.setContentType("application/json");
//            如果返回的是一个null springMVC会认为是自己给出了响应，不再使用springMVC提供的响应
            return null;
        }
//        如果不是ajax请求：需要将异常信息传入模型对象中，给出一个相应的错误视图
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
