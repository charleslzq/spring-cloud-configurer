package com.github.charleslzq.spring.cloud.configurer.client;

import com.github.charleslzq.spring.cloud.configurer.event.ConfigClientReadyEvent;
import com.github.charleslzq.spring.cloud.configurer.event.ConfigServerReadyEvent;
import com.github.charleslzq.spring.cloud.configurer.util.AnnotationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
@Configuration
@RemoteApplicationEventScan(basePackages = {"com.github.charleslzq.spring.cloud.configurer.event"})
public class ConfigClientConfiguration {

    @Autowired(required = false)
    private List<RemoteConfigurable> configurables = new ArrayList<>();

    private Map<String, List<RemoteConfigurable>> configs = new HashMap<>();

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.cloud.autoconfig.server}")
    private String configServerName;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        if (configurables.size() > 0) {
            configs = configurables.stream()
                    .collect(Collectors.groupingBy(AnnotationUtil::getConfigName));
        }
    }

    @Bean
    public ConfigRefresher configRefresher() {
        return new ConfigRefresher(configs);
    }

    @EventListener(
            classes = {ApplicationReadyEvent.class}
    )
    public void listener(ApplicationReadyEvent event) {
        applicationContext = event.getApplicationContext();
        publishClientReadyEvent();
    }

    @EventListener(
            classes = {ConfigServerReadyEvent.class}
    )
    public void listen(ConfigServerReadyEvent event) {
        publishClientReadyEvent();
    }

    private void publishClientReadyEvent() {
        if (applicationContext != null) {
            List<String> remoteConfigs = configs.keySet().stream().collect(Collectors.toList());
            applicationContext.publishEvent(new ConfigClientReadyEvent(
                    this, applicationContext.getId(), configServerName,
                    applicationName, remoteConfigs
            ));
        }
    }

}
