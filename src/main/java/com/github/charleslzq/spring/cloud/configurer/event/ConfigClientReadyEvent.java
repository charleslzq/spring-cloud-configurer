package com.github.charleslzq.spring.cloud.configurer.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhengqi on 2/7/2017.
 */
public class ConfigClientReadyEvent extends RemoteApplicationEvent {
    private final String appName;
    private final List<String> configs;

    public ConfigClientReadyEvent() {
        super();
        appName = "application";
        configs = new ArrayList<>();
    }

    public ConfigClientReadyEvent(Object source, String originService, String destinationService,
                                  String applicationName, List<String> remoteConfigs) {
        super(source, originService, destinationService);
        appName = applicationName;
        configs = remoteConfigs;
    }

    public String getAppName() {
        return appName;
    }

    public List<String> getConfigs() {
        return configs;
    }
}
