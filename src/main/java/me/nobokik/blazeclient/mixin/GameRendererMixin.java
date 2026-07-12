package me.nobokik.blazeclient.mixin;

import me.nobokik.blazeclient.Client;
import me.nobokik.blazeclient.mod.GeneralSettings;
import me.nobokik.blazeclient.mod.mods.HurtCamMod;
import me.nobokik.blazeclient.mod.mods.ZoomMod;
import net.minecraft.client.renderer.Camera;
import net.minecraft.client.renderer.GameRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerEntity;
import net.minecraft.world.phys.Direction;
import net.minecraft.world.phys.MathHelper;
import net.minecraft.world.phys.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;"), method = "tiltViewWhenHurt", require = 4)
    public float changeBobIntensity(float value) {
        return Client.modManager().getMod(HurtCamMod.class).isEnabled() ? Client.modManager().getMod(HurtCamMod.class).scale.getFValue() * value : value;
    }

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void disableHurtCam(PoseStack matrices, float tickDelta, CallbackInfo ci) {
        if (Client.modManager().getMod(HurtCamMod.class).isEnabled() && Client.modManager().getMod(HurtCamMod.class).disableHurtcam.isEnabled()) ci.cancel();
    }

    @Redirect(method = "tiltViewWhenHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDamageTiltYaw()F"))
    public float changeHurtCamType(LivingEntity instance) {
        if(Client.modManager().getMod(HurtCamMod.class).isEnabled() && Client.modManager().getMod(HurtCamMod.class).oldHurtcam.isEnabled())
            return 0;
        else
            return instance.getDamageTiltYaw();
    }

    @Inject(at = @At("RETURN"), method = "getFov", cancellable = true)
    public void onGetFOVModifier(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        double defaultFOV = cir.getReturnValue();
        cir.setReturnValue(Client.modManager().getMod(ZoomMod.class).getFOV(defaultFOV));
    }

    @Inject(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/PoseStack;translate(FFF)V"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void minimalViewBob(PoseStack matrices, float tickDelta, CallbackInfo ci, PlayerEntity playerEntity, float f, float g, float h) {
        if (Client.modManager().getMod(GeneralSettings.class).minimalViewBob.isEnabled()) {
            g /= 2;
            h /= 2;
            matrices.translate(MathHelper.sin(g * (float) Math.PI) * h * 0.5F, -Math.abs(MathHelper.cos(g * (float) Math.PI) * h), 0.0F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin(g * (float) Math.PI) * h * 3.0F));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(Math.abs(MathHelper.cos(g * (float) Math.PI - 0.2F) * h) * 5.0F));
            ci.cancel();
        }
    }
}