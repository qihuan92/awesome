package com.qihuan.service.impl;

import com.qihuan.pojo.User;
import com.qihuan.repository.UserRepository;
import com.qihuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * UserServiceImpl
 * Created by Qi on 2017/3/15.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void register(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> userList() {
        return userRepository.findAll();
    }


}