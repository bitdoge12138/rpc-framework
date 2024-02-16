package com.chen.register.impl;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.chen.loadbalancer.LoadBalancer;
import com.chen.loadbalancer.impl.RandomLoadBalancer;
import com.chen.register.ServiceDiscovery;
import com.chen.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;


import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author bitdoge
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {


    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if(loadBalancer == null) this.loadBalancer = new RandomLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }


    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }

}
