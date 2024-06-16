package cn.iaimi.crpc.loadbalancer;

import cn.iaimi.crpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 随机负载均衡器
 *
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/16
 */
public class RandomLoadBalancer implements LoadBalancer {

    private final Random random = new Random();


    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        int size = serviceMetaInfoList.size();
        if (0 == size)
            return null;

        if (1 == size)
            return serviceMetaInfoList.get(0);

        return serviceMetaInfoList.get(random.nextInt(size));
    }
}
