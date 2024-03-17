package cn.iaimi.cloverrpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/17
 */
public class KryoSerializer implements Serializer {

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL =
            ThreadLocal.withInitial(() -> {
                Kryo kryo = new Kryo();
                // 设置动态序列化和反序列化类，不提前注册所有类
                kryo.setRegistrationRequired(false);
                return kryo;
            });

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new Output(byteArrayOutputStream)) {
            KRYO_THREAD_LOCAL.get().writeObject(output, obj);
            return byteArrayOutputStream.toByteArray();
        }

    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try (Input input = new Input(byteArrayInputStream)) {
            T res = KRYO_THREAD_LOCAL.get().readObject(input, type);
            return res;
        }
    }
}
