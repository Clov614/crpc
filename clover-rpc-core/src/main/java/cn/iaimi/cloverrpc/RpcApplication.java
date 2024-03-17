package cn.iaimi.cloverrpc;

import cn.iaimi.cloverrpc.config.RpcConfig;
import cn.iaimi.cloverrpc.constant.RpcConstant;
import cn.iaimi.cloverrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());
    }

    public static void init() {
        RpcConfig newRpcConfig;

        try {
            newRpcConfig = ConfigUtils.load(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    public static RpcConfig getRpcConfig() {
        if (null == rpcConfig) {
            synchronized (RpcApplication.class) {
                if (null == rpcConfig) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
