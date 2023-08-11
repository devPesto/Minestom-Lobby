package io.github.pesto;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.velocity.VelocityProxy;

public class Lobby {

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();
        Settings settings = new Settings();

        switch (settings.runMode()) {
            case "online":
                MojangAuth.init();
            case "velocity":
                VelocityProxy.enable(settings.());
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