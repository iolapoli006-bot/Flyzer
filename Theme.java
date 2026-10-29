package dev.flyzer.gui;

import net.minecraft.client.gui.DrawContext;

public final class Theme {
    public static final int BG = 0xCC140A24;
    public static final int BG_DARK = 0xEE0D0618;
    public static final int BORDER = 0xFF4B2A7A;
    public static final int ACCENT = 0xFF9D4EDD;
    public static final int ACCENT_SOFT = 0xAA7B2CBF;
    public static final int TEXT = 0xFFE0AAFF;
    public static final int TEXT_DIM = 0xFF9C7BC4;

    public static void outline(DrawContext c, int x, int y, int w, int h, int col) {
        c.fill(x, y, x + w, y + 1, col);
        c.fill(x, y + h - 1, x + w, y + h, col);
        c.fill(x, y, x + 1, y + h, col);
        c.fill(x + w - 1, y, x + w, y + h, col);
    }

    public static void panel(DrawContext c, int x, int y, int w, int h) {
        c.fill(x, y, x + w, y + h, BG);
        c.fill(x, y, x + 2, y + h, ACCENT);
    }

    private Theme() {}
}
