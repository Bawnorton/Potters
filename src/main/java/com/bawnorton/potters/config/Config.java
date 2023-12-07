package com.bawnorton.potters.config;

import com.bawnorton.potters.config.annotation.IntOption;

public class Config {
    private static Config LOCAL_INSTANCE = new Config();
    private static Config SERVER_INSTANCE = new Config();

    @IntOption(value = 3, min = 1)
    public int copperStackCount;
    @IntOption(value = 4, min = 1)
    public int ironStackCount;
    @IntOption(value = 12, min = 1)
    public int lapisStackCount;
    @IntOption(value = 16, min = 1)
    public int redstoneStackCount;
    @IntOption(value = 20, min = 1)
    public int goldStackCount;
    @IntOption(value = 24, min = 1)
    public int quartzStackCount;
    @IntOption(value = 32, min = 1)
    public int amethystStackCount;
    @IntOption(value = 48, min = 1)
    public int emeraldStackCount;
    @IntOption(value = 64, min = 1)
    public int diamondStackCount;
    @IntOption(value = 256, min = 1)
    public int netheriteStackCount;

    private Config() {
    }

    public static Config getLocalInstance() {
        return LOCAL_INSTANCE;
    }

    public static Config getServerInstance() {
        return SERVER_INSTANCE;
    }

    static void updateLocal(Config config) {
        LOCAL_INSTANCE = config;
    }

    static void updateServer(Config config) {
        SERVER_INSTANCE = config;
    }
}
