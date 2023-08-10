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


}