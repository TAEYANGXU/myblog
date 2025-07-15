package com.xyz.myblog.service;

import com.xyz.myblog.entity.User;
import com.xyz.myblog.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("尝试登录用户名: {}", username);
        User user = userMapper.findByUsername(username);
        if (user == null) {
            logger.warn("用户名不存在: {}", username);
            throw new UsernameNotFoundException("用户名不存在");
        }
        logger.info("查到用户: {}，密码: {}", user, user.getPassword());
        return new LoginUser(user);
    }
}
