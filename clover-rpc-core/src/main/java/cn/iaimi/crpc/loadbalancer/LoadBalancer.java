package cn.iaimi.crpc.loadbalancer;

import cn.iaimi.crpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/16
 *
 * 负载均衡器（消费端使用）
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     * @param requestParams 请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return 返回选中服务
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
