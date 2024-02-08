package com.chen.test;


import com.chen.api.UserService;
import com.chen.netty.server.NettyServer;
import com.chen.register.DefaultServiceRegistry;
import com.chen.register.ServiceRegistry;
import com.chen.serializer.impl.JsonSerializer;
import com.chen.service.UserServiceImpl;

public class NettyTestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(userService);
        NettyServer server = new NettyServer();
        server.setSerializer(new JsonSerializer());
        server.start(9999);
    }

}
