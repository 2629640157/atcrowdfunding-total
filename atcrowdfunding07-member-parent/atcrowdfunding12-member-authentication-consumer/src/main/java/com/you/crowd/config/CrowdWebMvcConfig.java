package com.you.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 游斌
 * @create 2020-07-30  20:18
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器访问的地址
        String urlPath = "/member/to/reg/page";
        //目标视图名，将来拼接"prefix:classpath:/templates/","suffix:.html"前后缀
        String viewName = "member-reg";
        //添加view-controller
        registry.addViewController(urlPath).setViewName(viewName);
        //addRedirectViewController：/login/page（urlPath） 这个请求全都重定向到"http://www.crowd.com/member/to/login/page"（redirectUrl）
        registry.addRedirectViewController("/login/page", "http://www.crowd.com/member/to/login/page");
        registry.addViewController("/member/to/login/page").setViewName("member-login");
        registry.addViewController("/member/to/main/page").setViewName("member-main");
        registry.addViewController("/member/to/crowd/page").setViewName("member-crowd");

    }
}
