package me.nobokik.blazeclient.mixin;
import me.nobokik.blazeclient.Client;
import me.nobokik.blazeclient.mod.GeneralSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.InGameOverlayRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    @Inject(method = "renderFireOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/PoseStack;push()V"))
    private static void renderFireOverlay(Minecraft client, PoseStack matrices, CallbackInfo ci) {
        if(!Client.modManager().getMod(GeneralSettings.class).lowFire.isEnabled()) return;
        matrices.translate(0, -0.2F, 0);
    }
}
