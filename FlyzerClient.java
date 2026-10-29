package dev.flyzer;

import dev.flyzer.gui.FlyzerScreen;
import dev.flyzer.module.Modules;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FlyzerClient implements ClientModInitializer {
    public static KeyBinding menuKey, zoomKey;
    private static boolean zooming;
    private static int savedFov;

    @Override
    public void onInitializeClient() {
        Config.load();
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.flyzer.menu",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.flyzer"));
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.flyzer.zoom",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.flyzer"));

        ClientTickEvents.END_CLIENT_TICK.register(FlyzerClient::tick);
        HudRenderCallback.EVENT.register(HudRenderer::render);
        ClientLifecycleEvents.CLIENT_STOPPING.register(c -> Config.save());
    }

    private static void tick(MinecraftClient mc) {
        while (menuKey.wasPressed()) mc.setScreen(new FlyzerScreen());

        // Zoom (через FOV, мінімум 30)
        boolean want = Modules.ZOOM.enabled && zoomKey.isPressed() && mc.currentScreen == null;
        if (want && !zooming) {
            savedFov = mc.options.getFov().getValue();
            zooming = true;
        }
        if (zooming) {
            if (want) mc.options.getFov().setValue(30);
            else { mc.options.getFov().setValue(savedFov); zooming = false; }
        }

        if (mc.player == null || mc.world == null) return;

        // Toggle Sprint
        if (Modules.SPRINT.enabled && mc.options.forwardKey.isPressed() && !mc.player.isSneaking()
                && !mc.player.isUsingItem() && !mc.player.horizontalCollision
                && mc.player.getHungerManager().getFoodLevel() > 6) {
            mc.player.setSprinting(true);
        }

        // Time Changer (лише клієнтська сторона)
        if (Modules.TIME.enabled) {
            long[] t = {1000L, 6000L, 12500L, 18000L};
            mc.world.getLevelProperties().setTimeOfDay(t[Modules.TIME.mode]);
        }
    }
}
