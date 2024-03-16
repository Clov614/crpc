package cn.iaimi.cloverrpc.serializer;

import java.io.IOException;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 *
 * 序列化接口
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param object
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化
     * @param bytes
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
}
