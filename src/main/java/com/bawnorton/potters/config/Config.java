package com.bawnorton.potters.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {
    private static Config INSTANCE;

    public static Config getInstance() {
        if (INSTANCE == null) INSTANCE = new Config();
        return INSTANCE;
    }

    public static void update(Config config) {
        INSTANCE = config;
    }

    @Expose
    public Integer numberOfStacks = 1;


    @Override
    public String toString() {
        return "Config{" +
                "numberOfStacks=" + numberOfStacks +
                '}';
    }
}
