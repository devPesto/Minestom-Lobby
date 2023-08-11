package io.github.pesto;

import io.github.pesto.event.Listen;
import io.github.pesto.event.Listener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Lobby {
    private static final Logger logger = LoggerFactory.getLogger(Lobby.class);

    public static void main(String[] args) {
        Settings settings = new Settings(logger);

        MinecraftServer server = MinecraftServer.init();
        InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.setChunkLoader(new AnvilLoader(Path.of("myworld")));
        instance.loadChunk(new Pos(2, 65, -3));

        new GlobalListener(instance).register();

        String secret = settings.forwardingSecret();
        if (secret.isBlank()) {
            logger.info("Velocity forwarding secret is not configured. Running in online mode");
            MojangAuth.init();
        } else {
            VelocityProxy.enable(settings.forwardingSecret());
        }

        server.start(settings.host(), settings.port());
    }

    public static class GlobalListener implements Listener {
        private final Instance world;

        public GlobalListener(Instance world) {
            this.world = world;
        }

        @Listen(event = PlayerLoginEvent.class)
        public void onLogin(PlayerLoginEvent event) {
            Player player = event.getPlayer();

            player.setFlying(true);
            player.setRespawnPoint(new Pos(2, 65, -3)); // TODO: Configure spawn point
            event.setSpawningInstance(world);
        }

    }
}