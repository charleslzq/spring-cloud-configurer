package com.github.charleslzq.spring.cloud.configurer.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
@Configuration
public class ClientListenerConfiguration {
    @Autowired(required = false)
    private List<RemoteConfigurer> configurers = new ArrayList<>();

    private Map<String, RemoteConfigurer> configurerMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        configurerMap = configurers.stream()
                .collect(Collectors.toMap(
                        RemoteConfigurer::configName,
                        Function.identity()
                ));
    }

    @Bean
    public ClientReadyListener clientReadyListener(ConfigClientResolver resolver) {
        return new ClientReadyListener(resolver,
                configurerMap
        );
    }
}
