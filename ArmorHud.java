package dev.flyzer.module;

import dev.flyzer.gui.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ArmorHud extends HudModule {
    private static final EquipmentSlot[] SLOTS =
            {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public ArmorHud(int x, int y) { super("Armor Status", "Shows armor and durability", x, y); }

    @Override
    public void render(DrawContext c, MinecraftClient mc) {
        w = 22;
        h = 4 * 18 + 6;
        Theme.panel(c, x, y, w, h);
        for (int i = 0; i < 4; i++) {
            ItemStack s = mc.player.getEquippedStack(SLOTS[i]);
            if (s.isEmpty()) continue;
            c.drawItem(s, x + 4, y + 3 + i * 18);
            c.drawStackOverlay(mc.textRenderer, s, x + 4, y + 3 + i * 18);
        }
    }
}
