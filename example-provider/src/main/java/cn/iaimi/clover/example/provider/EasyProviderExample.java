package cn.iaimi.clover.example.provider;


import cn.iaimi.clover.example.common.service.UserService;
import cn.iaimi.crpc.RpcApplication;
import cn.iaimi.crpc.registry.LocalRegistry;
import cn.iaimi.crpc.server.VertHttpServer;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/15
 *
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {

        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        VertHttpServer httpServer = new VertHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
