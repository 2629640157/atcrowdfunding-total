package com.you.crowd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 游斌
 * @create 2020-07-10  16:13
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //表示请用全局方法权限控制功能
public class CrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CrowUserDetailsService crowUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(crowUserDetailsService).passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bootstrap/**",
                        "/comment/**",
                        "/css/**",
                        "/fonts/**",
                        "/img/**",
                        "/jquery/**",
                        "/js/**",
                        "/layer/**",
                        "/pagination/**",
                        "/script/**",
                        "/ztree/**"
                )
                .permitAll()
                .antMatchers("/admin/to/user.html")
                .hasRole("经理")
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable() //禁用csrf
                .formLogin()
                .loginPage("/admin/to/admin-login.html")
                .loginProcessingUrl("/security/do/login.html")
                .defaultSuccessUrl("/admin/to/main.html")
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/security/do/loginOut.html")
                .logoutSuccessUrl("/admin/to/admin-login.html")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("message", "对不起，您没有对应的权限！");
                        httpServletRequest.getRequestDispatcher("/WEB-INF/errors/system-error.jsp").forward(httpServletRequest, httpServletResponse);
                    }
                })
        ;
    }
}
