package com.github.charleslzq.spring.cloud.configurer.server;

import com.github.charleslzq.spring.cloud.configurer.event.RemoteConfigUpdateEvent;
import com.google.gson.Gson;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
public class ConfigChangePublisher {

    private ConfigClientResolver resolver;

    private ApplicationContext publisher;

    private List<ConfigChangePublishInterceptor> interceptors;

    private Gson gson = new Gson();

    public ConfigChangePublisher(ConfigClientResolver resolver,
                                 ApplicationContext context,
                                 List<ConfigChangePublishInterceptor> interceptors) {
        this.resolver = resolver;
        this.publisher = context;
        this.interceptors = interceptors;
    }

    private void publish(RemoteConfigUpdateEvent event) {
        if (interceptors.size() == 0 ||
                interceptors.stream().allMatch(interceptor -> interceptor.beforePublish(event))) {
            publisher.publishEvent(event);
            interceptors.forEach(interceptor -> interceptor.afterPublish(event));
        }
    }

    public void publishAdd(String target, String configName, Object config) {
        publish(
                new RemoteConfigUpdateEvent.Add(
                        this, publisher.getId(), target,
                        configName, gson.toJson(config)
                )
        );
    }

    public void publishAdd(String configName, Object config) {
        resolver.resolveTargets(configName).forEach(
                target -> publishAdd(target, configName, config)
        );
    }

    public void publishModify(String target, String configName, Object config) {
        publish(
                new RemoteConfigUpdateEvent.Modify(
                        this, publisher.getId(), target,
                        configName, gson.toJson(config)
                )
        );
    }

    public void publishModify(String configName, Object config) {
        resolver.resolveTargets(configName).forEach(
                target -> publishModify(target, configName, config)
        );
    }

    public void publishDelete(String target, String configName, Object config) {
        publish(
                new RemoteConfigUpdateEvent.Delete(
                        this, publisher.getId(), target,
                        configName, gson.toJson(config)
                )
        );
    }

    public void publishDelete(String configName, Object config) {
        resolver.resolveTargets(configName).forEach(
                target -> publishDelete(target, configName, config)
        );
    }
}
