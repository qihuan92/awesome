package com.qihuan.service;

import com.qihuan.pojo.User;

import java.util.List;

/**
 * UserService
 * Created by Qi on 2017/3/15.
 */

public interface UserService {
    void register(User user);

    User getUser(String userName);

    List<User> userList(int page, int size);
}
