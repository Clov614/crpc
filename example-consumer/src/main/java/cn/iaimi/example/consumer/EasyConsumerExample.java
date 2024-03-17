package cn.iaimi.example.consumer;

import cn.iaimi.clover.example.common.model.User;
import cn.iaimi.clover.example.common.service.UserService;
import cn.iaimi.crpc.config.RpcConfig;
import cn.iaimi.crpc.proxy.ServiceProxyFactory;
import cn.iaimi.crpc.utils.ConfigUtils;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/15
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.load(RpcConfig.class, "rpc");
        System.out.println(rpc);
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("Clover614");
        // invoking call
        User newUser = userService.getUser(user);
        if (null != newUser) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("null == user");
        }
//        long number = userService.getNum();
//        System.out.println(number);
    }
}
