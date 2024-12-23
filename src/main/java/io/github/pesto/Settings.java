package io.github.pesto;

import com.moandjiezana.toml.Toml;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Settings {
    private final Path path = Path.of("settings.toml");
    private final Toml config;

    public Settings(Logger logger) {
        checkFile(logger);
        this.config = new Toml().read(path.toFile());
    }

    private void checkFile(Logger logger) {
        if (!Files.exists(path)) {
            logger.info("Could not find settings config. Copying default config...");
            try (InputStream is = getClass().getResourceAsStream("/settings.toml")) {
                if (is == null)
                    throw new IOException("Could not find default config");

                Files.copy(is, path);
            } catch (IOException e) {
				logger.error("IOException: {}", e.getMessage());
            }
        }
    }

    public String host() {
        return config.getString("host");
    }

    public int port() {
        return config.getLong("port").intValue();
    }

    public String forwardingSecret() {
        return config.getString("forwarding-secret");
    }

}
