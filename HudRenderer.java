package dev.flyzer;

import dev.flyzer.gui.HudEditorScreen;
import dev.flyzer.module.Cps;
import dev.flyzer.module.HudModule;
import dev.flyzer.module.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public final class HudRenderer {
    public static void render(DrawContext c, RenderTickCounter tc) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen == null) Cps.poll(mc.getWindow().getHandle());
        if (mc.options.hudHidden || mc.player == null || mc.currentScreen instanceof HudEditorScreen) return;
        drawAll(c, mc);
    }

    public static void drawAll(DrawContext c, MinecraftClient mc) {
        if (mc.player == null) return;
        for (HudModule h : Modules.huds()) if (h.enabled) h.render(c, mc);
    }
}
