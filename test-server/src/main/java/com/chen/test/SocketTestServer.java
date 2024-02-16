package com.chen.test;

import com.chen.api.UserService;
import com.chen.serializer.CommonSerializer;
import com.chen.service.UserServiceImpl2;
import com.chen.socket.server.SocketServer;
import com.chen.service.UserServiceImpl;

public class SocketTestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl2();

        SocketServer socketServer = new SocketServer("127.0.0.1", 9998, CommonSerializer.KRYO_SERIALIZER);

        socketServer.publishService(userService, UserService.class);

    }
}
