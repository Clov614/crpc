package cn.iaimi.cloverrpc.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 * 配置工具类
 */
public class ConfigUtils {

    /**
     * 加载配置对象
     *
     * @param tClass
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuffer configFileBuilder = new StringBuffer("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        // 支持多种格式获取
        // todo 这里解析yml会有问题
        Props props = null;
        boolean isYml = false;
        try {
            props = Props.getProp(configFileBuilder.toString() + ".properties", CharsetUtil.UTF_8);
        } catch (Exception e) {
            try {
                isYml = true;
                props = Props.getProp(configFileBuilder.toString() + ".yml", CharsetUtil.UTF_8);
            } catch (Exception ex) {
                props = Props.getProp(configFileBuilder.toString() + ".yaml", CharsetUtil.UTF_8);
            }
        }
        props.autoLoad(true); // 配置更新自动获取
        if (!isYml) return props.toBean(tClass, prefix);
        return props.toBean(tClass, prefix);
    }
}
