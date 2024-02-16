package com.chen.test;


import com.chen.api.UserService;
import com.chen.netty.server.NettyServer;
import com.chen.serializer.CommonSerializer;
import com.chen.service.UserServiceImpl;

public class NettyTestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();


        NettyServer nettyServer = new NettyServer("127.0.0.1", 9999, CommonSerializer.KRYO_SERIALIZER);

        nettyServer.publishService(userService, UserService.class);

    }

}
