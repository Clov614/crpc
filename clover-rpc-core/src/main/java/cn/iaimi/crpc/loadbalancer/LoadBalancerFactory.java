package cn.iaimi.crpc.loadbalancer;

import cn.iaimi.crpc.spi.SpiLoader;

/**
 * 负载均衡工厂 （获取负载均衡对象）
 *
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/16
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取负载均衡对象
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
