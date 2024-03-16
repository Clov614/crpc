package cn.iaimi.cloverrpc.proxy;

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
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
