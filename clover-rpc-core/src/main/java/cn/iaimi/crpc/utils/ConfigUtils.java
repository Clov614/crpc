package cn.iaimi.crpc.utils;

import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/16
 * 配置工具类
 */
@Slf4j
public class ConfigUtils {

    private static final String BASE_PATH_DIR = "conf/";
    private static final String BASE_CONF_FILE_NAME = "application";
    private static final String PROPERTIES_EXT = ".properties";
    private static final String YAML_EXT = ".yaml";
    private static final String YML_EXT = ".yml";
    private static final String ENV_SPLIT = "-";

    /**
     * 加载配置对象
     *
     * @param clazz
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T load(Class<T> clazz, String prefix) {
        return load(clazz, prefix, "");
    }

    public static <T> T load(Class<T> clazz, String prefix, String env) {
        T props;
        return ((props = loadProperties(clazz, prefix, env)) != null ?
                props : loadYaml(clazz, prefix, env));
    }

    /**
     * 加载 properties 配置 application-{env}.properties
     * <p>
     * 优先加载 yml配置
     * </P>
     *
     * @param prefix
     * @param env
     * @param <T>
     * @return
     */
    public static <T> T loadProperties(Class<T> clazz, String prefix, String env) {
        try {
            return doLoadProperties(clazz, BASE_PATH_DIR + BASE_CONF_FILE_NAME, prefix, env);
        } catch (NoResourceException e) {
            log.warn("Not exists properties conf file in [{}], will load properties file from classpath",
                    BASE_PATH_DIR);
        }
        try {
            return doLoadProperties(clazz, BASE_CONF_FILE_NAME, prefix, env);
        } catch (NoResourceException e) {
            log.warn("Not exists properties conf file, will load yaml/yml file from classpath");
        }
        return null;
    }

    /**
     * 加载 yaml 配置 application-{env}.yaml / application-{env}.yml
     *
     * @param clazz
     * @param prefix
     * @param env
     * @param <T>
     * @return
     */
    public static <T> T loadYaml(Class<T> clazz, String prefix, String env) {
        try {
            return doLoadYaml(clazz, BASE_PATH_DIR + BASE_CONF_FILE_NAME, prefix, env, YAML_EXT);
        } catch (NoResourceException e) {
            log.warn("Not exists yaml conf file in [{}], will load yml file from /conf",
                    BASE_PATH_DIR);
        }
        try {
            return doLoadYaml(clazz, BASE_PATH_DIR + BASE_CONF_FILE_NAME, prefix, env, YML_EXT);
        } catch (NoResourceException e) {
            log.warn("Not exists yml conf file in [{}], will load yaml file from classpath",
                    BASE_PATH_DIR);
        }
        try {
            return doLoadYaml(clazz, BASE_CONF_FILE_NAME, prefix, env, YAML_EXT);
        } catch (NoResourceException e) {
            log.warn("Not exists yaml conf file in classpath, will load yml file from classpath");
        }
        try {
            return doLoadYaml(clazz, BASE_CONF_FILE_NAME, prefix, env, YML_EXT);
        } catch (NoResourceException e) {
            log.error("not conf file!");
            throw e;
        }
    }

    private static <T> T doLoadProperties(Class<T> clazz, String base, String prefix, String env) {
        String configFilePath = buildConfigFilePath(base, env, PROPERTIES_EXT);
        Props props = new Props(configFilePath, CharsetUtil.UTF_8);
        props.autoLoad(true); // 动态加载配置
        return props.toBean(clazz, prefix);
    }

    private static <T> T doLoadYaml(Class<T> clazz, String base, String prefix, String env, String ext) {
        String configFilePath = buildConfigFilePath(base, env, ext);
        Dict props = YamlUtil.loadByPath(configFilePath);
        JSONObject rpcConfigProps = JSONUtil.parseObj(props).getJSONObject(prefix);
        return JSONUtil.toBean(rpcConfigProps, clazz);
    }

    /**
     * 拼接文件类路径
     *
     * @param base
     * @param env
     * @param ext
     * @return classpath
     */
    private static String buildConfigFilePath(String base, String env, String ext) {
        StringBuilder sb = new StringBuilder(base);
        if (StrUtil.isNotBlank(env)) {
            sb.append(ENV_SPLIT).append(env);
        }
        sb.append(ext);
        return sb.toString();
    }
}
