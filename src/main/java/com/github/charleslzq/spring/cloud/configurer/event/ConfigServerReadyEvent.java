package com.github.charleslzq.spring.cloud.configurer.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * Created by liuzhengqi on 2/7/2017.
 */
public class ConfigServerReadyEvent extends RemoteApplicationEvent {

    public ConfigServerReadyEvent() {
        super();
    }

    public ConfigServerReadyEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }
}
