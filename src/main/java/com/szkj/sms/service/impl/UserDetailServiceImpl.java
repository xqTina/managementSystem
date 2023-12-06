package com.szkj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.szkj.sms.entity.User;
import com.szkj.sms.exception.UserUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName: UserDetailServiceImpl
 * @author: sixiwenwu
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailServiceImpl(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", s)
                .or()
                .eq("phone", s);
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            //判断账户是否可用
            if (user.getIsUse() == 0 || user.getExpireDate() != null && LocalDateTime.now().isAfter(user.getExpireDate())) {
                throw new UserUnavailableException("该用户已过期或已停用");
            }
            //创建一个权限的集合
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            //添加获取权限
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
            //把对象信息（用户名，密码，权限）存入对象，返回该对象，controller层直接调用
            return new org.springframework.security.core.userdetails.User(user.getUsername(), passwordEncoder.encode(user.getPwdhash()), authorities);
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}
