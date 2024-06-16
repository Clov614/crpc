package cn.iaimi.crpc.loadbalancer;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/16
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";

}
