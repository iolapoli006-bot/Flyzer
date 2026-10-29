package dev.flyzer.module;

import dev.flyzer.module.Module.Category;
import net.minecraft.client.network.PlayerListEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Modules {
    public static final List<Module> ALL = new ArrayList<>();

    // Visuals
    public static final Module FULLBRIGHT = add(new Module("Fullbright", "Maximum brightness everywhere", Category.VISUALS));
    public static final Module ZOOM = add(new Module("Zoom", "Hold the Zoom key (C) to zoom in", Category.VISUALS));
    public static final Module TIME = add(new Module("Time Changer", "Client-side time (right-click = change)", Category.VISUALS)
            .modes("Day", "Noon", "Sunset", "Night"));

    // Movement
    public static final Module SPRINT = add(new Module("Toggle Sprint", "Auto-sprint while moving forward", Category.MOVEMENT));

    // HUD
    public static final HudModule FPS = add(new TextHud("FPS", "Frames per second", 6, 6,
            mc -> mc.getCurrentFps() + " FPS"));
    public static final HudModule CPS = add(new TextHud("CPS", "Clicks per second", 6, 24,
            mc -> Cps.left() + " | " + Cps.right() + " CPS"));
    public static final HudModule COORDS = add(new TextHud("Coordinates", "XYZ position", 6, 42,
            mc -> String.format(Locale.ROOT, "XYZ %.0f %.0f %.0f", mc.player.getX(), mc.player.getY(), mc.player.getZ())));
    public static final HudModule PING = add(new TextHud("Ping", "Server latency", 6, 60, mc -> {
        PlayerListEntry e = mc.getNetworkHandler() == null ? null
                : mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        return (e == null ? 0 : e.getLatency()) + " ms";
    }));
    public static final HudModule DIRECTION = add(new TextHud("Direction", "Facing direction", 6, 78, mc -> {
        String d = mc.player.getHorizontalFacing().asString();
        return d.substring(0, 1).toUpperCase(Locale.ROOT) + d.substring(1)
                + String.format(Locale.ROOT, " (%.0f)", net.minecraft.util.math.MathHelper.wrapDegrees(mc.player.getYaw()));
    }));
    public static final HudModule ARMOR = add(new ArmorHud(6, 100));
    public static final HudModule KEYS = add(new KeystrokesHud(40, 100));
    public static final HudModule POTIONS = add(new PotionHud(120, 6));

    static {
        FPS.enabled = true;
        CPS.enabled = true;
        COORDS.enabled = true;
        ARMOR.enabled = true;
        KEYS.enabled = true;
    }

    private static <T extends Module> T add(T m) { ALL.add(m); return m; }

    public static List<HudModule> huds() {
        List<HudModule> l = new ArrayList<>();
        for (Module m : ALL) if (m instanceof HudModule h) l.add(h);
        return l;
    }
}
