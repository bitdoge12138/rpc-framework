package com.chen.test;

import com.chen.RpcClient;
import com.chen.api.User;
import com.chen.api.UserService;
import com.chen.RpcClientProxy;
import com.chen.serializer.impl.KryoSerializer;
import com.chen.socket.client.SocketClient;


public class TestClient {

    public static void main(String[] args) {

        RpcClient client = new SocketClient("127.0.0.1", 9000);
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        UserService userService = proxy.getProxy(UserService.class);
        User user = new User("123", "chen");
        String res = userService.hello(user);
        System.out.println(res);
    }
}
