package com.sirarchlinux.runelite.polybar;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PolybarIntegrationPluginTest {

    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(PolybarIntegrationPlugin.class);
        RuneLite.main(args);
    }
}
