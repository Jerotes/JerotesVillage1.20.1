package com.jerotes.jerotesvillage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StructureFilterConfig {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/jerotesvillage/structure_filter.json");
    private static final Map<ResourceLocation, DimensionConfig> DIMENSION_CONFIGS = new HashMap<>();

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            createDefaultConfig();
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            JsonObject dimensions = root.getAsJsonObject("dimensions");
            if (dimensions != null) {
                for (String key : dimensions.keySet()) {
                    ResourceLocation dimId = ResourceLocation.tryParse(key);
                    if (dimId == null) continue;
                    JsonObject entry = dimensions.getAsJsonObject(key);
                    Set<String> whitelist = new HashSet<>();
                    Set<String> blacklist = new HashSet<>();
                    if (entry.has("whitelist")) {
                        entry.getAsJsonArray("whitelist").forEach(e -> whitelist.add(e.getAsString()));
                    }
                    if (entry.has("blacklist")) {
                        entry.getAsJsonArray("blacklist").forEach(e -> blacklist.add(e.getAsString()));
                    }
                    DIMENSION_CONFIGS.put(dimId, new DimensionConfig(whitelist, blacklist));
                }
            }
            LOGGER.info("Loaded structure filter config for {} dimensions", DIMENSION_CONFIGS.size());
        } catch (Exception e) {
            LOGGER.error("Failed to load structure filter config", e);
            DIMENSION_CONFIGS.clear();
        }
    }

    private static void createDefaultConfig() {
        CONFIG_FILE.getParentFile().mkdirs();
        JsonObject root = new JsonObject();
        JsonObject dims = new JsonObject();

        JsonObject example = new JsonObject();
        example.add("whitelist", GSON.toJsonTree(Arrays.asList("minecraft", "jerotesvillage", "echostruct", "jerotes_village_golems")));
        example.add("blacklist", GSON.toJsonTree(Arrays.asList("")));
        dims.add("jerotesvillage:second_round_world", example);

        root.add("dimensions", dims);
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to create default config", e);
        }
    }

    public static boolean isStructureAllowed(ResourceLocation dimensionId, String structureModId) {
        DimensionConfig config = DIMENSION_CONFIGS.get(dimensionId);
        if (config == null) {
            return true;
        }
        if (config.blacklist.contains(structureModId)) {
            return false;
        }
        if (!config.whitelist.isEmpty()) {
            return config.whitelist.contains(structureModId);
        }
        return true;
    }

    private static class DimensionConfig {
        final Set<String> whitelist;
        final Set<String> blacklist;

        DimensionConfig(Set<String> whitelist, Set<String> blacklist) {
            this.whitelist = whitelist;
            this.blacklist = blacklist;
        }
    }
}