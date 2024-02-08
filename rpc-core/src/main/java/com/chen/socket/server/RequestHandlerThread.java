package com.chen.socket.server;

import com.chen.register.ServiceRegistry;
import com.chen.rpc.entity.RpcRequest;
import com.chen.rpc.entity.RpcResponse;
import com.chen.RequestHandler;
import com.chen.serializer.CommonSerializer;
import com.chen.socket.util.ObjectReader;
import com.chen.socket.util.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;


@Slf4j
public class RequestHandlerThread implements Runnable {

    private Socket socket;

    private RequestHandler requestHandler;

    private ServiceRegistry serviceRegistry;

    private CommonSerializer serializer;
    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry, CommonSerializer serializer) {

        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
        this.serializer = serializer;

    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(rpcRequest, service);
            RpcResponse<Object> response = RpcResponse.success(result, rpcRequest.getRequestId());
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            log.error("调用或发送时有错误发生：", e);
        }
    }
}
