package com.chen.socket.server;

import com.chen.rpc.entity.RpcRequest;
import com.chen.rpc.entity.RpcResponse;
import com.chen.handler.RequestHandler;
import com.chen.serializer.CommonSerializer;
import com.chen.socket.util.ObjectReader;
import com.chen.socket.util.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;


@Slf4j
public class SocketRequestHandlerThread implements Runnable {

    private final Socket socket;

    private final RequestHandler requestHandler;

    private final CommonSerializer serializer;
    public SocketRequestHandlerThread(Socket socket, RequestHandler requestHandler, CommonSerializer serializer) {

        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serializer = serializer;

    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            Object result = requestHandler.handle(rpcRequest);
            RpcResponse<Object> response = RpcResponse.success(result, rpcRequest.getRequestId());
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            log.error("调用或发送时有错误发生：", e);
        }
    }
}
