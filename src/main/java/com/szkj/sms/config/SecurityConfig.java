package com.szkj.sms.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.szkj.sms.dto.DataZhenXianJiNewDto;
import com.szkj.sms.entity.*;
import com.szkj.sms.filter.CaptchaFilter;
import com.szkj.sms.handler.MyAuthenticationFailureHandler;
import com.szkj.sms.handler.MyAuthenticationSuccessHandler;
import com.szkj.sms.service.*;
import com.szkj.sms.service.impl.UserDetailServiceImpl;
import com.szkj.sms.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SecurityConfig
 * @Description:
 * @author: sixiwenwu
 * @date: 2021/9/18 11:24
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataZhenxianjiService dataZhenxianjiService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DtuService dtuService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceUserService deviceUserService;

    @Autowired
    private DataZhenxianjiUnitService dataZhenxianjiUnitService;
    private final MyAuthenticationSuccessHandler authenticationSuccessHandler;
    private final MyAuthenticationFailureHandler authenticationFailHandler;
    private final CaptchaFilter captchaFilter;
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public SecurityConfig(MyAuthenticationSuccessHandler authenticationSuccessHandler, MyAuthenticationFailureHandler authenticationFailHandler, CaptchaFilter captchaFilter, UserDetailServiceImpl userDetailService) {
        this.authenticationFailHandler = authenticationFailHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.captchaFilter = captchaFilter;
        this.userDetailService = userDetailService;
//        this.alreadyLoginFilter = alreadyLoginFilter;

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 权限设置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(alreadyLoginFilter, UsernamePasswordAuthenticationFilter.class);

        // 允许iframe正常加载
        http.headers().frameOptions().sameOrigin();
        // 关闭CSRF防跨站攻击
        http.csrf().disable();
        // 认证规则
        http.authorizeRequests()
                // 非匿名用户都可以访问
                .antMatchers("/", "/index", "welcome", "/chart/**", "/welcomeInfo", "/datatable/**", "/device/show", "/device/table", "/device/user/bind", "/device/user/removeBind","/user/device_dtu").hasAnyRole("ADMIN", "DEV", "USER")
                // 管理员可用访问
                .antMatchers("/dtu/**", "/loginHistory/**", "/loginHistory", "/user/**", "/device/edit/show", "/device/delete","/message/email/send/test","/message/email/config","/message/email/edit").hasAnyRole("ADMIN", "DEV")
                // 所有人可以访问
                .antMatchers("/code").permitAll()
                // 开发项目功能页只能开发人员访问
                .antMatchers("/swagger**/**", "/webjars/**", "/v3/**", "/doc.html", "/druid/**").hasRole("DEV");


        // 开启默认的登录页,不开启则是默认报403 Access Denied
        http.formLogin()
                .loginPage("/tologin")
                .loginProcessingUrl("/login")
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailHandler);

        //开启注销功能
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/outlogin"));

        //开启记住我功能
        http.rememberMe().rememberMeParameter("remember_me");
    }

    /**
     * 用户认证
     *
     * @param auth 认证对象
     * @throws Exception E
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());



    }
}
