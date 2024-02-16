package com.chen.provider.impl;

import com.chen.provider.ServiceProvider;
import com.chen.rpc.enumeration.RpcError;
import com.chen.rpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class ServiceProviderImpl implements ServiceProvider {

    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>(); // 接口-服务 Map
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();
    @Override
    public synchronized <T> void addServiceProvider(T service) {

        String serviceName = service.getClass().getCanonicalName();  // 返回 Java 语言规范定义的基础类的规范名称

        if (registeredService.contains(serviceName)) return;

        registeredService.add(serviceName);

        Class<?>[] interfaces = service.getClass().getInterfaces();

        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }

        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }

        log.info("向接口:{}注册服务:{}", interfaces, serviceName);


    }

    @Override
    public synchronized Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);

        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }

        return service;
    }
}
