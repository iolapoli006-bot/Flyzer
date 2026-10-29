package dev.flyzer.gui;

import dev.flyzer.Config;
import dev.flyzer.module.HudModule;
import dev.flyzer.module.Modules;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {
    private final Screen parent;
    private HudModule dragging;
    private int offX, offY;

    public HudEditorScreen(Screen parent) {
        super(Text.literal("Flyzer HUD Editor"));
        this.parent = parent;
    }

    @Override
    public void renderBackground(DrawContext c, int mx, int my, float d) {
        c.fill(0, 0, width, height, 0x66100020);
    }

    @Override
    public void render(DrawContext c, int mx, int my, float delta) {
        renderBackground(c, mx, my, delta);
        for (HudModule h : Modules.huds()) {
            if (!h.enabled) continue;
            h.render(c, client);
            Theme.outline(c, h.x - 1, h.y - 1, h.w + 2, h.h + 2,
                    h == dragging ? 0xFFFFFFFF : Theme.ACCENT);
        }
        c.drawCenteredTextWithShadow(textRenderer, "Drag elements to move them - ESC to save and go back",
                width / 2, height - 16, Theme.TEXT);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            var list = Modules.huds();
            for (int i = list.size() - 1; i >= 0; i--) {
                HudModule h = list.get(i);
                if (h.enabled && mx >= h.x && mx <= h.x + h.w && my >= h.y && my <= h.y + h.h) {
                    dragging = h;
                    offX = (int) mx - h.x;
                    offY = (int) my - h.y;
                    return true;
                }
            }
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
        if (dragging != null) {
            dragging.x = Math.max(0, Math.min(width - dragging.w, (int) mx - offX));
            dragging.y = Math.max(0, Math.min(height - dragging.h, (int) my - offY));
            return true;
        }
        return super.mouseDragged(mx, my, button, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mx, double my, int button) {
        dragging = null;
        return super.mouseReleased(mx, my, button);
    }

    @Override
    public void close() { client.setScreen(parent); }

    @Override
    public void removed() { Config.save(); }

    @Override
    public boolean shouldPause() { return false; }
}
