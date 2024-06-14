package cn.iaimi.crpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.iaimi.crpc.RpcApplication;
import cn.iaimi.crpc.config.RpcConfig;
import cn.iaimi.crpc.constant.RpcConstant;
import cn.iaimi.crpc.model.RpcRequest;
import cn.iaimi.crpc.model.RpcResponse;
import cn.iaimi.crpc.model.ServiceMetaInfo;
import cn.iaimi.crpc.registry.Registry;
import cn.iaimi.crpc.registry.RegistryFactory;
import cn.iaimi.crpc.serializer.Serializer;
import cn.iaimi.crpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

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

        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(rpcConfig.getVersion()); // 旧： RpcConstant
            List<ServiceMetaInfo> serviceMetaInfoList =
                    registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
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
