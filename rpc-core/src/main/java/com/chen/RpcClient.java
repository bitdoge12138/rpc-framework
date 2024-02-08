package com.chen;

import com.chen.rpc.entity.RpcRequest;
import com.chen.serializer.CommonSerializer;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);

}
