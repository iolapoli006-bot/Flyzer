package dev.flyzer.module;

public class Module {
    public enum Category {
        VISUALS("Visuals"), MOVEMENT("Movement"), HUD("HUD");
        public final String label;
        Category(String l) { this.label = l; }
    }

    public final String name, description;
    public final Category category;
    public boolean enabled;
    public String[] modes;
    public int mode;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Module modes(String... m) { this.modes = m; return this; }
    public void toggle() { enabled = !enabled; }
    public void cycleMode() { if (modes != null) mode = (mode + 1) % modes.length; }
    public String modeName() { return modes == null ? "" : modes[mode]; }
}
