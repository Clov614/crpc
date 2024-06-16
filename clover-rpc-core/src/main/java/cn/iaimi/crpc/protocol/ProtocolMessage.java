package cn.iaimi.crpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage<T> {

    private Header header;

    private T body;

    @Data
    public static class Header {

        /**
         * 魔数，保证数据安全
         */
        private byte magic;

        /**
         * 版本号
         */
        private byte version;

        /**
         * 序列化器
         */
        private byte serializer;

        /**
         * 消息类型（请求/响应）
         */
        private byte type;

        /**
         * 状态
         */
        private byte status;

        /**
         * 请求ID
         */
        private long requestId;

        /**
         * 消息长度
         */
        private int bodyLength;

    }
}
