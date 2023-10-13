package com.bawnorton.potters.config;

import com.bawnorton.potters.Potters;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
    private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve(Potters.MOD_ID + ".json");

    public static void loadConfig() {
        Config config = load();

        if(config.numberOfStacks == null || config.numberOfStacks < 1 || config.numberOfStacks > Math.pow(2, 25)) {
            config.numberOfStacks = 1;
        }

        Config.update(config);
        save();
        Potters.LOGGER.debug("Config loaded");
    }

    private static Config load() {
        Config config = Config.getInstance();
        try {
            if (!Files.exists(configPath)) {
                Files.createDirectories(configPath.getParent());
                Files.createFile(configPath);
                return config;
            }
            try {
                config = GSON.fromJson(Files.newBufferedReader(configPath), Config.class);
            } catch (JsonSyntaxException e) {
                Potters.LOGGER.error("Failed to parse config file, using default config");
                config = new Config();
            }
        } catch (IOException e) {
            Potters.LOGGER.error("Failed to load config", e);
        }
        return config;
    }

    private static void save() {
        try {
            Files.write(configPath, GSON.toJson(Config.getInstance()).getBytes());
        } catch (IOException e) {
            Potters.LOGGER.error("Failed to save config", e);
        }
    }

    public static void saveConfig() {
        save();
        Potters.LOGGER.debug("Saved config");
    }
}
