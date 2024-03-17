package cn.iaimi.cloverrpc.config;

import lombok.Data;

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

    private Integer serverPort = 8080;

    private Boolean mock = false;

    private String serializer = "jdk";
}
