package com.sirarchlinux.runelite.polybar;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "Polybar Integration"
)
public class PolybarIntegrationPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private PolybarIntegrationConfig config;

    @Override
    protected void startUp() {

    }

    @Override
    protected void shutDown() {

    }

    @Provides
    PolybarIntegrationConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PolybarIntegrationConfig.class);
    }
}
