package com.github.charleslzq.spring.cloud.configurer.event;

import com.github.charleslzq.spring.cloud.configurer.client.RemoteConfigurable;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * Created by liuzhengqi on 2/7/2017.
 */
public abstract class RemoteConfigUpdateEvent extends RemoteApplicationEvent {
    private final String configName;
    private final String payload;

    public RemoteConfigUpdateEvent() {
        super();
        configName = null;
        payload = null;
    }

    public RemoteConfigUpdateEvent(Object source, String originService, String destinationService,
                                   String configName, String data
    ) {
        super(source, originService, destinationService);
        this.configName = configName;
        payload = data;
    }

    public abstract void process(RemoteConfigurable remoteConfigurable, Object config);

    public String getConfigName() {
        return configName;
    }

    public String getPayload() {
        return payload;
    }

    public static class Add extends RemoteConfigUpdateEvent {
        public Add() {
            super();
        }

        public Add(Object source, String originService, String destinationService,
                   String configName, String data) {
            super(source, originService, destinationService, configName, data);
        }

        @Override
        public void process(RemoteConfigurable remoteConfigurable, Object config) {
            remoteConfigurable.onAdd(config);
        }
    }

    public static class Modify extends RemoteConfigUpdateEvent {
        public Modify() {
            super();
        }

        public Modify(Object source, String originService, String destinationService,
                      String configName, String data) {
            super(source, originService, destinationService, configName, data);
        }

        @Override
        public void process(RemoteConfigurable remoteConfigurable, Object config) {
            remoteConfigurable.onModify(config);
        }
    }

    public static class Delete extends RemoteConfigUpdateEvent {
        public Delete() {
            super();
        }

        public Delete(Object source, String originService, String destinationService,
                      String configName, String data) {
            super(source, originService, destinationService, configName, data);
        }

        @Override
        public void process(RemoteConfigurable remoteConfigurable, Object config) {
            remoteConfigurable.onDelete(config);
        }
    }
}
