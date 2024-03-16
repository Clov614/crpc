package cn.iaimi.clover.example.provider;

import cn.iaimi.clover.example.common.model.User;
import cn.iaimi.clover.example.common.service.UserService;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/15
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("[provider] 用户名: " + user.getName());
        user.setName("[new] " + user.getName());
        return user;
    }
}
