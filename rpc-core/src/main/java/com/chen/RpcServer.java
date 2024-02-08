package com.chen;

import com.chen.serializer.CommonSerializer;

public interface RpcServer {
    void start(int port);

    void setSerializer(CommonSerializer serializer);
}
