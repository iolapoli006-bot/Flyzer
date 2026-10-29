package dev.flyzer.gui;

import dev.flyzer.Config;
import dev.flyzer.module.Module;
import dev.flyzer.module.Module.Category;
import dev.flyzer.module.Modules;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class FlyzerScreen extends Screen {
    private static final int PW = 320, PH = 250, ROW = 26;
    private Category tab = Category.VISUALS;
    private int px, py;

    public FlyzerScreen() { super(Text.literal("Flyzer")); }

    @Override
    protected void init() {
        px = (width - PW) / 2;
        py = (height - PH) / 2;
    }

    private List<Module> rows() {
        List<Module> l = new ArrayList<>();
        for (Module m : Modules.ALL) if (m.category == tab) l.add(m);
        return l;
    }

    @Override
    public void renderBackground(DrawContext c, int mx, int my, float d) {
        c.fill(0, 0, width, height, 0x99100020);
    }

    @Override
    public void render(DrawContext c, int mx, int my, float delta) {
        renderBackground(c, mx, my, delta);
        c.fill(px, py, px + PW, py + PH, Theme.BG_DARK);
        Theme.outline(c, px, py, PW, PH, Theme.ACCENT);
        c.fill(px, py, px + PW, py + 22, Theme.ACCENT_SOFT);
        c.drawTextWithShadow(textRenderer, "FLYZER", px + 10, py + 7, 0xFFFFFFFF);
        c.drawTextWithShadow(textRenderer, "1.21.4", px + PW - 40, py + 7, Theme.TEXT);

        // Tabs
        int tx = px + 10;
        for (Category cat : Category.values()) {
            boolean sel = cat == tab;
            c.fill(tx, py + 28, tx + 90, py + 46, sel ? Theme.ACCENT_SOFT : Theme.BG);
            Theme.outline(c, tx, py + 28, 90, 18, sel ? Theme.ACCENT : Theme.BORDER);
            c.drawCenteredTextWithShadow(textRenderer, cat.label, tx + 45, py + 33, sel ? 0xFFFFFFFF : Theme.TEXT);
            tx += 96;
        }

        // Rows
        int y = py + 54;
        for (Module m : rows()) {
            if (y + ROW > py + PH - 30) break;
            boolean hover = mx >= px + 10 && mx <= px + PW - 10 && my >= y && my < y + ROW - 2;
            c.fill(px + 10, y, px + PW - 10, y + ROW - 2, hover ? 0xAA2A1450 : Theme.BG);
            c.fill(px + 10, y, px + 12, y + ROW - 2, m.enabled ? Theme.ACCENT : Theme.BORDER);
            c.drawTextWithShadow(textRenderer, m.name + (m.modes != null ? " [" + m.modeName() + "]" : ""),
                    px + 18, y + 4, Theme.TEXT);
            c.drawTextWithShadow(textRenderer, m.description, px + 18, y + 14, Theme.TEXT_DIM);
            // switch
            int sx = px + PW - 44, sy = y + 7;
            c.fill(sx, sy, sx + 26, sy + 10, m.enabled ? Theme.ACCENT : 0xFF2A1A44);
            c.fill(m.enabled ? sx + 16 : sx, sy, m.enabled ? sx + 26 : sx + 10, sy + 10, 0xFFFFFFFF);
            y += ROW;
        }

        // HUD editor button
        boolean hb = inEditorBtn(mx, my);
        c.fill(px + 10, py + PH - 26, px + PW - 10, py + PH - 8, hb ? Theme.ACCENT : Theme.ACCENT_SOFT);
        c.drawCenteredTextWithShadow(textRenderer, "HUD Editor", px + PW / 2, py + PH - 21, 0xFFFFFFFF);
    }

    private boolean inEditorBtn(double mx, double my) {
        return mx >= px + 10 && mx <= px + PW - 10 && my >= py + PH - 26 && my <= py + PH - 8;
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int tx = px + 10;
        for (Category cat : Category.values()) {
            if (mx >= tx && mx <= tx + 90 && my >= py + 28 && my <= py + 46) { tab = cat; return true; }
            tx += 96;
        }
        if (inEditorBtn(mx, my)) { client.setScreen(new HudEditorScreen(this)); return true; }
        int y = py + 54;
        for (Module m : rows()) {
            if (mx >= px + 10 && mx <= px + PW - 10 && my >= y && my < y + ROW - 2) {
                if (button == 0) m.toggle(); else if (button == 1) m.cycleMode();
                Config.save();
                return true;
            }
            y += ROW;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) { close(); return true; }
        return super.keyPressed(key, scan, mods);
    }

    @Override
    public void removed() { Config.save(); }

    @Override
    public boolean shouldPause() { return false; }
}
