package com.chen.test;

import com.chen.RpcClient;
import com.chen.RpcClientProxy;
import com.chen.api.User;
import com.chen.api.UserService;
import com.chen.netty.client.NettyClient;
import com.chen.serializer.CommonSerializer;

public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        UserService userService = rpcClientProxy.getProxy(UserService.class);
        User user = new User("123", "chen is good!");
        String res = userService.hello(user);
        System.out.println(res);
    }

}