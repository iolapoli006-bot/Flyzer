package dev.flyzer.module;

import dev.flyzer.gui.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class KeystrokesHud extends HudModule {
    public KeystrokesHud(int x, int y) { super("Keystrokes", "WASD + mouse buttons with CPS", x, y); }

    private void key(DrawContext c, MinecraftClient mc, int x, int y, int bw, String label, boolean down) {
        c.fill(x, y, x + bw, y + 20, down ? Theme.ACCENT_SOFT : Theme.BG);
        Theme.outline(c, x, y, bw, 20, down ? Theme.ACCENT : Theme.BORDER);
        c.drawCenteredTextWithShadow(mc.textRenderer, label, x + bw / 2, y + 6, down ? 0xFFFFFFFF : Theme.TEXT);
    }

    @Override
    public void render(DrawContext c, MinecraftClient mc) {
        w = 66;
        h = 64;
        var o = mc.options;
        key(c, mc, x + 23, y, 20, "W", o.forwardKey.isPressed());
        key(c, mc, x, y + 22, 20, "A", o.leftKey.isPressed());
        key(c, mc, x + 23, y + 22, 20, "S", o.backKey.isPressed());
        key(c, mc, x + 46, y + 22, 20, "D", o.rightKey.isPressed());
        key(c, mc, x, y + 44, 32, Cps.left() + " L", Cps.leftDown());
        key(c, mc, x + 34, y + 44, 32, Cps.right() + " R", Cps.rightDown());
    }
}
