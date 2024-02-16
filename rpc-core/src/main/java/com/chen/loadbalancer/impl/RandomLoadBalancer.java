package com.chen.loadbalancer.impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.chen.loadbalancer.LoadBalancer;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer {

    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }

}