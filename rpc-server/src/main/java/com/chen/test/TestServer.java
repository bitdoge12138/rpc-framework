package com.chen.test;

import com.chen.api.UserService;
import com.chen.register.DefaultServiceRegistry;
import com.chen.register.ServiceRegistry;
import com.chen.serializer.impl.KryoSerializer;
import com.chen.socket.server.SocketServer;
import com.chen.service.UserServiceImpl;

public class TestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();

        serviceRegistry.register(userService);

        SocketServer socketServer = new SocketServer(serviceRegistry);

        socketServer.setSerializer(new KryoSerializer());

        socketServer.start(9000);



    }
}
