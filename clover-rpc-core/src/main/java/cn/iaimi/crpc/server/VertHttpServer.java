package cn.iaimi.crpc.server;

import io.vertx.core.Vertx;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/15
 */
public class VertHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        // 创建Vert.x实例
        Vertx vertx = Vertx.vertx();

        // 创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 端口监听并处理请求
        server.requestHandler(new HttpServerHandler());

        // 启动 HTTP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.out.println("Fail to start server: " + result.cause());
            }
        });
    }
}
