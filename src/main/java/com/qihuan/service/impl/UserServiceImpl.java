package com.qihuan.service.impl;

import com.qihuan.exception.ApiException;
import com.qihuan.security.bean.SecurityUser;
import com.qihuan.pojo.User;
import com.qihuan.repository.UserRepository;
import com.qihuan.security.JwtTokenUtil;
import com.qihuan.service.UserService;
import com.qihuan.tools.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * UserServiceImpl
 * Created by Qi on 2017/3/15.
 */

@Service
public class UserServiceImpl implements UserService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public UserServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ApiException(ResultEnum.USER_EXISTED);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        user.setLastPasswordResetDate(new Date());
        user.setRoles(Arrays.asList("ROLE_USER"));
        userRepository.insert(user);
    }

    @Override
    public User getUser(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new ApiException(ResultEnum.USER_NO_EXIST);
        }
        return user;
    }

    @Override
    public List<User> userList(int page, int size) {
        return userRepository.findAll(new PageRequest(page, size)).getContent();
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

}
