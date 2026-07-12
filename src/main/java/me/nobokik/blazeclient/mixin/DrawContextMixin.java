package me.nobokik.blazeclient.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.SharedConstants;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiGraphicsExtractor.class)
public class DrawContextMixin {
    @Shadow
    public int drawText(Font Font, @Nullable String string, int i, int j, int k, boolean bl) {
        return 0;
    }

    @Inject(at = @At("HEAD"), method = "drawTextWithShadow(Lnet/minecraft/client/font/Font;Ljava/lang/String;III)I", cancellable = true)
    public void drawTextWithShadow(Font Font, String string, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
        if (string.startsWith("Minecraft " + SharedConstants.getGameVersion().getName())) cir.setReturnValue(drawText(Font, "", i, j, k, true));
    }
}
