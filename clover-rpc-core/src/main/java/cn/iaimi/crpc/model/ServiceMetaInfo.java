package cn.iaimi.crpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/13
 *
 * 服务元信息（注册信息）
 * 可理解为服务端点
 */
@Data
public class ServiceMetaInfo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion;

    /**
     * 服务域名
     */
    private String serviceHost;

    /**
     * 服务端口号
     */
    private int servicePort;

    /**
     * 服务分组 （暂未实现）
     */
    private String serviceGroup = "default";

    /**
     * 获取服务键名
     * @return
     */
    public String getServiceKey() {
        // 后续可扩展服务分组
//        return String.format("%s:%s:%s", serviceName, serviceVersion, serviceGroup);
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点键名
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%d", getServiceKey(), serviceHost, servicePort);
    }

    /**
     * 获取完整服务地址
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%d", serviceHost, servicePort);
        }
        return String.format("%s:%d", serviceHost, servicePort);
    }

    /**
     * 设置 服务器地址
     * @param serviceHost 服务器主机地址
     * @param servicePort 对应端口号
     */
    public void setServiceAddress(String serviceHost, int servicePort) {
        setServiceHost(serviceHost);
        setServicePort(servicePort);
    }
}
