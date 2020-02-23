package com.sirarchlinux.runelite.polybar;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("polybarintegration")
public interface PolybarIntegrationConfig extends Config {

    @ConfigItem(
            keyName = "module",
            name = "Module",
            description = "The name of the Polybar module"
    )
    default String module() {
        return "runelite";
    }

    @ConfigItem(
            keyName = "hook",
            name = "Hook",
            description = "Define which hook will be triggered"
    )
    @Range
    default int hook() {
        return 1;
    }

    @ConfigItem(
            keyName = "idle_only",
            name = "Idle only",
            description = "Only differs between idling and not idling"
    )
    default boolean isIdleOnly() {
        return false;
    }
}
