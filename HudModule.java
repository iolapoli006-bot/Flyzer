package dev.flyzer.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public abstract class HudModule extends Module {
    public int x, y, w = 60, h = 14;

    public HudModule(String name, String desc, int x, int y) {
        super(name, desc, Category.HUD);
        this.x = x;
        this.y = y;
    }

    public abstract void render(DrawContext c, MinecraftClient mc);
}
