package dev.flyzer;

import com.google.gson.*;
import dev.flyzer.module.HudModule;
import dev.flyzer.module.Module;
import dev.flyzer.module.Modules;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.*;

public final class Config {
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("flyzer.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        try {
            if (!Files.exists(PATH)) return;
            JsonObject root = JsonParser.parseString(Files.readString(PATH)).getAsJsonObject();
            for (Module m : Modules.ALL) {
                if (!root.has(m.name)) continue;
                JsonObject o = root.getAsJsonObject(m.name);
                if (o.has("enabled")) m.enabled = o.get("enabled").getAsBoolean();
                if (o.has("mode")) m.mode = o.get("mode").getAsInt();
                if (m instanceof HudModule h) {
                    if (o.has("x")) h.x = o.get("x").getAsInt();
                    if (o.has("y")) h.y = o.get("y").getAsInt();
                }
            }
        } catch (Exception e) {
            System.err.println("[Flyzer] Failed to load config: " + e);
        }
    }

    public static void save() {
        try {
            JsonObject root = new JsonObject();
            for (Module m : Modules.ALL) {
                JsonObject o = new JsonObject();
                o.addProperty("enabled", m.enabled);
                if (m.modes != null) o.addProperty("mode", m.mode);
                if (m instanceof HudModule h) {
                    o.addProperty("x", h.x);
                    o.addProperty("y", h.y);
                }
                root.add(m.name, o);
            }
            Files.writeString(PATH, GSON.toJson(root));
        } catch (Exception e) {
            System.err.println("[Flyzer] Failed to save config: " + e);
        }
    }
}
