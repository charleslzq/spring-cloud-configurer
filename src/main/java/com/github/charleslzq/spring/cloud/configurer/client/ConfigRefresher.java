package com.github.charleslzq.spring.cloud.configurer.client;

import com.github.charleslzq.spring.cloud.configurer.event.RemoteConfigUpdateEvent;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzhengqi on 2/7/2017.
 */
@Slf4j
public class ConfigRefresher
        implements ApplicationListener<RemoteConfigUpdateEvent> {
    private final Map<String, List<RemoteConfigurable>> configurableMap;
    private final Gson gson = new Gson();

    public ConfigRefresher(Map<String, List<RemoteConfigurable>> configurableMap) {
        if (configurableMap != null) {
            this.configurableMap = configurableMap;
        } else {
            this.configurableMap = new HashMap<>();
        }
    }

    @Override
    public void onApplicationEvent(RemoteConfigUpdateEvent remoteConfigUpdateEvent) {
        String configName = remoteConfigUpdateEvent.getConfigName();
        List<RemoteConfigurable> configurables = configurableMap.get(configName);
        if (configurables == null || configurables.size() == 0) {
            log.warn("Can not find responsible configurable beans for config {}", configName);
        } else {
            for (RemoteConfigurable configurable : configurables) {
                try {
                    Class configClass = configurable.configClass();
                    String configStr = remoteConfigUpdateEvent.getPayload();
                    Object config = gson.fromJson(configStr, configClass);
                    remoteConfigUpdateEvent.process(configurable, config);
                } catch (Exception e) {
                    log.error("Error refresh config " + configName + " for class " + configurable.getClass().getName(), e);
                    e.printStackTrace();
                }
            }
        }
    }
}
