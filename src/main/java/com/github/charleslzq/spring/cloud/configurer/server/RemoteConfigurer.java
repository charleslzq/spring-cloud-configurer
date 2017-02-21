package com.github.charleslzq.spring.cloud.configurer.server;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
public interface RemoteConfigurer<T> {
    String configName();

    Iterable<T> getAllConfig();

    void onAdd(T config);

    void onDelete(T config);

    void onModify(T config);

    default void onInit() {
        getAllConfig().forEach(this::onAdd);
    }

    ;
}
