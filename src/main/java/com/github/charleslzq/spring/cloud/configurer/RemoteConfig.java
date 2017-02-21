package com.github.charleslzq.spring.cloud.configurer;

import java.lang.annotation.*;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteConfig {
    String value();
}
