package com.chen;

import com.chen.rpc.entity.RpcRequest;
import com.chen.serializer.CommonSerializer;

public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;
    Object sendRequest(RpcRequest rpcRequest);


}
