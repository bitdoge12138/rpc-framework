package com.chen.socket.client;

import com.chen.RpcClient;
import com.chen.loadbalancer.LoadBalancer;
import com.chen.loadbalancer.impl.RandomLoadBalancer;
import com.chen.register.ServiceDiscovery;
import com.chen.register.impl.NacosServiceDiscovery;
import com.chen.rpc.entity.RpcRequest;
import com.chen.rpc.entity.RpcResponse;
import com.chen.rpc.enumeration.ResponseCode;
import com.chen.rpc.enumeration.RpcError;
import com.chen.rpc.exception.RpcException;
import com.chen.rpc.util.RpcMessageChecker;
import com.chen.serializer.CommonSerializer;
import com.chen.socket.util.ObjectReader;
import com.chen.socket.util.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;


@Slf4j
public class SocketClient implements RpcClient {

    private final ServiceDiscovery serviceDiscovery;
    private final CommonSerializer serializer;


    public SocketClient() {
        this(DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }
    public SocketClient(LoadBalancer loadBalancer) {
        this(DEFAULT_SERIALIZER, loadBalancer);
    }

    public SocketClient(Integer serializer) {
        this.serviceDiscovery = new NacosServiceDiscovery(new RandomLoadBalancer());
        this.serializer = CommonSerializer.getByCode(serializer);
    }

    public SocketClient(Integer serializer, LoadBalancer loadBalancer) {
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
    }



    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            // 通过 Socket 获取输出流，用于向服务端发送数据
            OutputStream outputStream = socket.getOutputStream();
            // 通过 Socket 获取输入流，用于从服务端接收数据
            InputStream inputStream = socket.getInputStream();
            // 用自定义的 ObjectWriter 类将 rpcRequest 对象写入到输出流中
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            // 用自定义的 ObjectReader 类从输入流中读取数据并反序列化为对象
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            if (rpcResponse == null) {
                log.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                log.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse;
        } catch (IOException e) {
            log.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }


}
