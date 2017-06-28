package com.qihuan.repository;

import com.qihuan.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * UserRepository
 * Created by Qi on 2017/3/14.
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String userName);
}
