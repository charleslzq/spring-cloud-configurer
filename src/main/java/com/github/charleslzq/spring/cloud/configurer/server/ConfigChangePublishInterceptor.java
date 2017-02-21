package com.github.charleslzq.spring.cloud.configurer.server;


import com.github.charleslzq.spring.cloud.configurer.event.RemoteConfigUpdateEvent;

/**
 * Created by liuzhengqi on 2/9/2017.
 */
public interface ConfigChangePublishInterceptor {
    boolean beforePublish(RemoteConfigUpdateEvent event);

    void afterPublish(RemoteConfigUpdateEvent event);
}
