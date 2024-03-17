package cn.iaimi.crpc.serializer;

import cn.iaimi.crpc.spi.SpiLoader;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/17
 */
public class SerializerFactory {

    static { // todo 实现序列化器的懒加载 （懒汉式单例）
        SpiLoader.load(Serializer.class);
    }

    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取序列化器
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
