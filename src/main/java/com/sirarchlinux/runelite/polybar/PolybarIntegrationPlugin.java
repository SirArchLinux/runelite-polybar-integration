package com.sirarchlinux.runelite.polybar;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@PluginDescriptor(
        name = "Polybar Integration"
)
public class PolybarIntegrationPlugin extends Plugin {
    @Getter
    private State globalState = State.IDLE;

    @Inject
    private Client client;

    @Inject
    private PolybarIntegrationConfig config;

    @Inject
    private ScheduledExecutorService executorService;

    @Override
    protected void startUp() {
        writeState(State.IDLE);
        notifyPolybar();
    }

    @Override
    protected void shutDown() {
        writeState(State.IDLE);
        notifyPolybar();
    }

    private void writeState(State state) {
        try {
            PrintWriter writer = new PrintWriter("/tmp/rl_state", "UTF-8");
            writer.println(state.getMessage());
            writer.close();
        } catch (IOException e) {
            log.error("Failed to write to file /tmp/rl_state");
        }
    }

    private void notifyPolybar() {
        final List<String> commands = new ArrayList<>();
        commands.add("polybar-msg");
        commands.add("hook");
        commands.add("runelite");
        commands.add("1");
        executorService.submit(() ->
        {
            try {
                Process notificationProcess = new ProcessBuilder(commands.toArray(new String[0]))
                        .redirectErrorStream(true)
                        .start();

                boolean exited = notificationProcess.waitFor(500, TimeUnit.MILLISECONDS);
                if (exited && notificationProcess.exitValue() == 0) {
                    log.debug("polybar-msg command executed successfully");
                    return;
                }
            } catch (IOException | InterruptedException ex) {
                log.error("Execution of polybar-msg command threw exception", ex);
            }

            log.debug("polybar-msg returned with error");
        });
    }

    @Provides
    PolybarIntegrationConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PolybarIntegrationConfig.class);
    }
}
