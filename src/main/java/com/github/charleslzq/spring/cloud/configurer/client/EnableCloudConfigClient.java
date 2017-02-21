package com.github.charleslzq.spring.cloud.configurer.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ConfigClientConfiguration.class)
public @interface EnableCloudConfigClient {
}
