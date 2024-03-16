package cn.iaimi.example.consumer;

import cn.iaimi.clover.clovrpc.proxy.ServiceProxyFactory;
import cn.iaimi.clover.example.common.model.User;
import cn.iaimi.clover.example.common.service.UserService;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/15
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        // todo 需要获取UserService的实现类对象
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
    }
}
