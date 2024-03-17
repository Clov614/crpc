package cn.iaimi.cloverrpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.iaimi.cloverrpc.RpcApplication;
import cn.iaimi.cloverrpc.config.RpcConfig;
import cn.iaimi.cloverrpc.model.RpcRequest;
import cn.iaimi.cloverrpc.model.RpcResponse;
import cn.iaimi.cloverrpc.serializer.Serializer;
import cn.iaimi.cloverrpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            // todo 这里地址被硬编码了，需要使用注册中心和服务发现机制解决
            try (HttpResponse httpResponse = HttpRequest.post(jointPostUri())
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String jointPostUri() {
        // "http://localhost:8080"
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        StringBuffer sb = new StringBuffer();
        return sb.append("http://").append(rpcConfig.getServerHost()).append(":").append(rpcConfig.getServerPort()).toString();
    }
}
