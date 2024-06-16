package cn.iaimi.crpc.config;

import lombok.Data;

import javax.annotation.Resource;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 * RPC 框架设置
 */
@Data
public class RpcConfig {

    private String name = "crpc";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8081;

    private Boolean mock = false;

    private String serializer = "jdk";

    private RegistryConfig registryConfig = new RegistryConfig();
}
