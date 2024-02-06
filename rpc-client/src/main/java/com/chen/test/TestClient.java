package com.chen.test;

import com.chen.api.User;
import com.chen.api.UserService;
import com.chen.client.RpcClientProxy;


public class TestClient {

    public static void main(String[] args) {

        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        UserService userService = proxy.getProxy(UserService.class);
        User user = new User("123", "chen");
        String res = userService.hello(user);
        System.out.println(res);



    }
}
