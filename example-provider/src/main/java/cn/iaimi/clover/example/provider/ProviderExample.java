package cn.iaimi.clover.example.provider;

import cn.iaimi.clover.example.common.service.UserService;
import cn.iaimi.crpc.RpcApplication;
import cn.iaimi.crpc.config.RegistryConfig;
import cn.iaimi.crpc.config.RpcConfig;
import cn.iaimi.crpc.model.ServiceMetaInfo;
import cn.iaimi.crpc.registry.LocalRegistry;
import cn.iaimi.crpc.registry.Registry;
import cn.iaimi.crpc.registry.RegistryFactory;
import cn.iaimi.crpc.server.VertHttpServer;
import cn.iaimi.crpc.server.tcp.VertxTcpClient;
import cn.iaimi.crpc.server.tcp.VertxTcpServer;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/14
 *
 * 服务提供者示例
 */
public class ProviderExample {

    public static void main(String[] args) {

        RpcApplication.init();

        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到服务中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(rpcConfig.getVersion());
        serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost(), rpcConfig.getServerPort());

        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException("服务提供者注册服务失败", e);
        }

        // 启动 web 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8081);
    }
}
