package com.github.charleslzq.spring.cloud.configurer.server;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

import java.util.List;
import java.util.Set;

/**
 * Created by liuzhengqi on 2/21/2017.
 */
public class ConfigClientResolver {

    private SetMultimap<String, String> configTargets =
            Multimaps.synchronizedSetMultimap(HashMultimap.create());

    public synchronized void addEntry(String appName, List<String> configNames) {
        configNames.forEach(configName -> configTargets.put(configName, appName));
    }

    public Set<String> resolveTargets(String configName) {
        return configTargets.get(configName);
    }
}
