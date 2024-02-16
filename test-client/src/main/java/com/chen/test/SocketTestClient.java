package com.chen.test;

import com.chen.RpcClient;
import com.chen.api.User;
import com.chen.api.UserService;
import com.chen.RpcClientProxy;
import com.chen.serializer.CommonSerializer;
import com.chen.socket.client.SocketClient;


public class SocketTestClient {

    public static void main(String[] args) {

        RpcClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        UserService userService = proxy.getProxy(UserService.class);
        User user = new User("123", "chen");

        for (int i = 0; i < 20; i++) {
            String res = userService.hello(user);
            System.out.println(res);
        }

    }
}
