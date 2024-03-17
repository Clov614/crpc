package cn.iaimi.crpc.server;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/15
 *
 * HTTP 服务器接口
 */
public interface HttpServer {

    /**
     * 启动服务器
     * @param port
     */
    void doStart(int port);
}
