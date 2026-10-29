package dev.flyzer.mixin;

import dev.flyzer.module.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin {
    // Fullbright: підміняємо значення гами, коли модуль увімкнений
    @Inject(method = "getValue", at = @At("HEAD"), cancellable = true)
    private void flyzer$fullbright(CallbackInfoReturnable<Object> cir) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc != null && mc.options != null && Modules.FULLBRIGHT.enabled
                && (Object) this == mc.options.getGamma()) {
            cir.setReturnValue(16.0);
        }
    }
}
