package cn.iaimi.example.consumer;

import cn.iaimi.clover.example.common.model.User;
import cn.iaimi.clover.example.common.service.UserService;
import cn.iaimi.crpc.config.RpcConfig;
import cn.iaimi.crpc.proxy.ServiceProxyFactory;
import cn.iaimi.crpc.utils.ConfigUtils;
import org.junit.Test;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/14
 */
public class EasyConsumerExampleTest {

    @Test
    public void serviceDiscoveryCacheTest() throws InterruptedException {
        // 三次调用
        // 第一次调用discovery走注册中心请求获取节点信息（serviceMetaInfo）
        // 第二次走本地缓存
        // 下线服务提供者 第三次走注册中心请求（缓存已清除）
        doRpcRequest(1);
//        System.out.println("睡眠 30s 第一次");
//        Thread.sleep(30 * 1000L);
        doRpcRequest(2);
//        System.out.println("睡眠 30s 第二次");
//        Thread.sleep(30 * 1000L);
        doRpcRequest(3);
    }

    void doRpcRequest(int item) {
        RpcConfig rpc = ConfigUtils.load(RpcConfig.class, "rpc");
        System.out.println(rpc);
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName(String.format("%d--%s", item, "Clover614"));
        // invoking call
        User newUser = userService.getUser(user);
        if (null != newUser) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("null == user");
        }
    }
}
