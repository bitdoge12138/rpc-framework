package com.chen.loadbalancer.impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.chen.loadbalancer.LoadBalancer;

import java.util.List;

public class RoundRobinLoadBalancer implements LoadBalancer {

    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        if(index >= instances.size()) {
            index %= instances.size();
        }
        return instances.get(index ++);
    }

}
