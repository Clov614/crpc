package cn.iaimi.crpc.server.tcp;


import cn.iaimi.crpc.model.RpcRequest;
import cn.iaimi.crpc.model.RpcResponse;
import cn.iaimi.crpc.protocol.ProtocolMessage;
import cn.iaimi.crpc.protocol.ProtocolMessageDecoder;
import cn.iaimi.crpc.protocol.ProtocolMessageEncoder;
import cn.iaimi.crpc.protocol.ProtocolMessageTypeEnum;
import cn.iaimi.crpc.registry.LocalRegistry;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 */
public class TcpServerHandler implements Handler<NetSocket> {


    @Override
    public void handle(NetSocket netSocket) {

        TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
            //region 处理请求的代码
            // 接收请求，解码
            ProtocolMessage<RpcRequest> protocolMessage;

            try {
                protocolMessage = (ProtocolMessage<RpcRequest>)
                        ProtocolMessageDecoder.decode(buffer);
            } catch (IOException e) {
                throw new RuntimeException("协议消息解码错误");
            }

            RpcRequest rpcRequest = protocolMessage.getBody();

            // 处理请求
            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();

            try {
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.getDeclaredConstructor().newInstance(),
                        rpcRequest.getArgs());
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            // 发送响应，编码
            ProtocolMessage.Header header = protocolMessage.getHeader();
            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
            ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);

            try {
                Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                netSocket.write(encode);
            } catch (IOException e) {
                throw new RuntimeException("协议消息编码错误");
            }
            //endregion
        });

        netSocket.handler(bufferHandlerWrapper);
    }
}
