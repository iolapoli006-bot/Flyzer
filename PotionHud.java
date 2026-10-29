package dev.flyzer.module;

import dev.flyzer.gui.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class PotionHud extends HudModule {
    public PotionHud(int x, int y) { super("Potion Effects", "Active effects with timers", x, y); }

    @Override
    public void render(DrawContext c, MinecraftClient mc) {
        List<String> lines = new ArrayList<>();
        for (StatusEffectInstance e : mc.player.getStatusEffects()) {
            String time = e.isInfinite() ? "inf"
                    : String.format("%d:%02d", e.getDuration() / 1200, (e.getDuration() / 20) % 60);
            lines.add(e.getEffectType().value().getName().getString() + " " + (e.getAmplifier() + 1) + " - " + time);
        }
        if (lines.isEmpty()) lines.add("No effects");
        int max = 0;
        for (String s : lines) max = Math.max(max, mc.textRenderer.getWidth(s));
        w = max + 10;
        h = lines.size() * 11 + 5;
        Theme.panel(c, x, y, w, h);
        for (int i = 0; i < lines.size(); i++)
            c.drawTextWithShadow(mc.textRenderer, lines.get(i), x + 6, y + 4 + i * 11, Theme.TEXT);
    }
}
