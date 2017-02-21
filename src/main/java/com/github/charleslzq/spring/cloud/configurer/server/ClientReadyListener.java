package com.github.charleslzq.spring.cloud.configurer.server;

import com.github.charleslzq.spring.cloud.configurer.event.ConfigClientReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
public class ClientReadyListener
        implements ApplicationListener<ConfigClientReadyEvent> {

    private ConfigClientResolver resolver;

    private Map<String, RemoteConfigurer> configurerMap;

    public ClientReadyListener(ConfigClientResolver resolver,
                               Map<String, RemoteConfigurer> configurerMap) {
        this.resolver = resolver;
        this.configurerMap = configurerMap;
    }


    @Override
    public void onApplicationEvent(ConfigClientReadyEvent configClientReadyEvent) {
        String application = configClientReadyEvent.getAppName();
        List<String> configs = configClientReadyEvent.getConfigs();
        resolver.addEntry(application, configs);
        configs.stream()
                .filter(configurerMap::containsKey)
                .map(configurerMap::get)
                .forEach(RemoteConfigurer::onInit);
    }
}
