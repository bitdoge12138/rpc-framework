package com.chen.socket.server;

import com.chen.RpcServer;
import com.chen.hook.ShutdownHook;
import com.chen.provider.ServiceProvider;
import com.chen.handler.RequestHandler;
import com.chen.provider.impl.ServiceProviderImpl;
import com.chen.register.ServiceRegistry;
import com.chen.register.impl.NacosServiceRegistry;
import com.chen.rpc.enumeration.RpcError;
import com.chen.rpc.exception.RpcException;
import com.chen.rpc.factory.ThreadPoolFactory;
import com.chen.serializer.CommonSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


@Slf4j
public class SocketServer implements RpcServer {

    private final String host;
    private final int port;

    private final ExecutorService threadPool;
    private final CommonSerializer serializer;

    private final RequestHandler requestHandler = new RequestHandler();
    private final ServiceProvider serviceProvider;

    private final ServiceRegistry serviceRegistry;



    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;

        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);

    }



    @Override
    public <T> void publishService(T service, Class<T> serviceClass) {
        if(serializer == null) {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
            log.info("服务器启动...");
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                log.info("消费者连接:{}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("服务器启动时有错误发生：", e);
        }
    }
}
