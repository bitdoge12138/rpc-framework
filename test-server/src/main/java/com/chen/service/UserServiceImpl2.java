package com.chen.service;

import com.chen.api.User;
import com.chen.api.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl2 implements UserService {
    @Override
    public String hello(User user) {

        log.info("-----UserService2的一个实例被调用了-----");

        return user.getId() + "-" + user.getName();
    }
}
