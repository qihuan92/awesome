package com.qihuan.repository;

import com.qihuan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 * Created by Qi on 2017/3/14.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
