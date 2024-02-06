package com.chen.test;

import com.chen.api.UserService;
import com.chen.register.DefaultServiceRegistry;
import com.chen.register.ServiceRegistry;
import com.chen.server.RpcServer;
import com.chen.service.UserServiceImpl;

public class TestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();

        serviceRegistry.register(userService);

        RpcServer rpcServer = new RpcServer(serviceRegistry);

        rpcServer.start(9000);



    }
}
