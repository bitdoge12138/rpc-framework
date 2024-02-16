package com.chen.register.impl;

import com.alibaba.nacos.api.exception.NacosException;
import com.chen.register.ServiceRegistry;
import com.chen.rpc.enumeration.RpcError;
import com.chen.rpc.exception.RpcException;
import com.chen.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Nacos服务注册中心
 * @author bitdoge
 */

@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            log.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
