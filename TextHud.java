package dev.flyzer.module;

import dev.flyzer.gui.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.function.Function;

public class TextHud extends HudModule {
    private final Function<MinecraftClient, String> text;

    public TextHud(String name, String desc, int x, int y, Function<MinecraftClient, String> text) {
        super(name, desc, x, y);
        this.text = text;
    }

    @Override
    public void render(DrawContext c, MinecraftClient mc) {
        String s;
        try { s = text.apply(mc); } catch (Exception e) { s = name; }
        w = mc.textRenderer.getWidth(s) + 10;
        h = 14;
        Theme.panel(c, x, y, w, h);
        c.drawTextWithShadow(mc.textRenderer, s, x + 6, y + 3, Theme.TEXT);
    }
}
