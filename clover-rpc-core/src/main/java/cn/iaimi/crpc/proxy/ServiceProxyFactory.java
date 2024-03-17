package cn.iaimi.crpc.proxy;

import cn.iaimi.crpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> serviceClass) {

        // 根据mock配置决定是否返回mock代理对象
        if (RpcApplication.getRpcConfig().getMock()) {
            return getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

    private static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy());
    }
}
