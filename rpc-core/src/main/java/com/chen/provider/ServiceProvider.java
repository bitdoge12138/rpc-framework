package com.chen.provider;

public interface ServiceProvider {

    <T> void addServiceProvider(T service);

    Object getServiceProvider(String serviceName);


}
