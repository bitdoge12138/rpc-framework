package com.chen.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class RpcRequest implements Serializable {

    public RpcRequest() {}

    /**
     * 请求号
     */
    private String requestId;

    private String interfaceName;

    private String methodName;


    private Object[] parameters;


    private Class<?>[] paramTypes;

    /**
     * 是否是心跳包
     */
    private Boolean heartBeat;
}
