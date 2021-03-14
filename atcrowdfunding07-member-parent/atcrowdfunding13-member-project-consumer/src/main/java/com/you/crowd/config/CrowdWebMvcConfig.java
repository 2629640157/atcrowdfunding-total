package com.you.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 游斌
 * @create 2020-08-05  16:52
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

// view-controller 是在 project-consumer 内部定义的，所以这里是一个不经过 Zuul访问的地址，所以这个路径前面不加路由规则中定义的前缀：“/project”
        registry.addViewController("/to/agree/page").setViewName("project-agree");
        registry.addViewController("/to/step1/page").setViewName("project-step1");
        registry.addViewController("/to/step2/page").setViewName("project-step2");
        registry.addViewController("/to/step3/page").setViewName("project-step3");
        registry.addViewController("/to/step4/page").setViewName("project-step4");
//        registry.addViewController("/to/step4/page").setViewName("project-show-detail");
    }
}
