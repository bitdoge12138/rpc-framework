package com.chen.hook;


import com.chen.rpc.factory.ThreadPoolFactory;
import com.chen.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShutdownHook {


    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }

    public void addClearAllHook() {
        log.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            ThreadPoolFactory.shutDownAll();
        }));
    }

}
