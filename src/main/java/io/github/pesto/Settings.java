package io.github.pesto;

import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Settings {
    private final Path path = Path.of("settings.conf");
    private ConfigurationNode config;

    public Settings() {
        checkFile();
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .path(path)
                .build();

        try {
            config = loader.load();
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    private void checkFile() {
        if (!Files.exists(path))
            try (InputStream is = getClass().getResourceAsStream("settings.conf")) {
                Files.copy(is, path);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public String host() {
        return config.node("host").getString();
    }

    public int port() {
        return config.node("port").getInt();
    }

    public String runMode() {
        return config.node("run-mode").getString();
    }

    public String forwardingSecret() {
        return config.node("forwarding-secret").getString();
    }

    public enum RunMode {
        ONLINE, OFFLINE, VELOCITY;
    }

}
