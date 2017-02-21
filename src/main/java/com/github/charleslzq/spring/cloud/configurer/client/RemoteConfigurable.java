package com.github.charleslzq.spring.cloud.configurer.client;

import com.google.common.reflect.TypeToken;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
public interface RemoteConfigurable<T> {

    default Class configClass() {
        return new TypeToken<T>(getClass()) {
        }.getRawType();
    }

    void onAdd(T config);

    void onModify(T config);

    void onDelete(T config);
}
