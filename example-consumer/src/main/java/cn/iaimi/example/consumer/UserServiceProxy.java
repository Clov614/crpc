package cn.iaimi.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.iaimi.clover.clovrpc.model.RpcRequest;
import cn.iaimi.clover.clovrpc.model.RpcResponse;
import cn.iaimi.clover.clovrpc.serializer.JdkSerializer;
import cn.iaimi.clover.example.common.model.User;
import cn.iaimi.clover.example.common.service.UserService;

import java.io.IOException;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        JdkSerializer serializer = new JdkSerializer();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
