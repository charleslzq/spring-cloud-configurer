package com.github.charleslzq.spring.cloud.configurer.util;

import com.github.charleslzq.spring.cloud.configurer.RemoteConfig;
import org.springframework.util.StringUtils;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
public class AnnotationUtil {

    public static String getConfigName(Object configClass) {
        RemoteConfig[] remoteConfigs =
                configClass.getClass().getAnnotationsByType(RemoteConfig.class);
        if (remoteConfigs.length == 0) {
            throw new IllegalArgumentException("Need to provide config name using @RemoteConfig for " + configClass.getClass().getName());
        }
        String configName = remoteConfigs[0].value();
        if (StringUtils.isEmpty(configName)) {
            throw new IllegalArgumentException("Config name can not be empty");
        }

        return configName;
    }
}
