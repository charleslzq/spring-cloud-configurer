package com.github.charleslzq.spring.cloud.configurer.server;

import com.github.charleslzq.spring.cloud.configurer.event.ConfigServerReadyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
@Configuration
@RemoteApplicationEventScan(basePackages = {"com.github.charleslzq.spring.cloud.configurer.event"})
public class ConfigServerConfiguration {

    @Autowired(required = false)
    private List<ConfigChangePublishInterceptor> interceptors = new ArrayList<>();

    @Autowired
    private ApplicationContext context;


    @Bean
    public ConfigClientResolver configClientResolver() {
        return new ConfigClientResolver();
    }

    @Bean
    public ConfigChangePublisher configChangePublisher() {
        return new ConfigChangePublisher(configClientResolver(), context, interceptors);
    }

    @EventListener(
            classes = {ApplicationReadyEvent.class}
    )
    public void listener(ApplicationReadyEvent event) {
        ApplicationContext context = event.getApplicationContext();
        context.publishEvent(
                new ConfigServerReadyEvent(
                        this, context.getId(), "*:**"
                )
        );
    }
}
