package com.bawnorton.potters.networking;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class Networking {
    private static MinecraftServer server;

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTED.register(Networking::setServer);
    }

    public static void setServer(MinecraftServer server) {
        Networking.server = server;
    }

    public static boolean isDedicated() {
        if (server == null) return false;
        return server.isDedicated();
    }
}
