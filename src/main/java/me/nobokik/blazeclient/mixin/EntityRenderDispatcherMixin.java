package me.nobokik.blazeclient.mixin;

import me.nobokik.blazeclient.Client;
import me.nobokik.blazeclient.mod.mods.HitboxMod;
import net.minecraft.client.renderer.VertexConsumer;
import net.minecraft.client.renderer.VertexConsumerProvider;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.world.entity.boss.dragon.EnderDragonPart;
import net.minecraft.world.phys.Box;
import net.minecraft.world.phys.MathHelper;
import net.minecraft.world.phys.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Shadow public abstract void setRenderHitboxes(boolean renderHitboxes);

    @Inject(at = @At("HEAD"), method = "renderHitbox", cancellable = true)
    private static void renderHitbox(PoseStack PoseStack, VertexConsumer vertexConsumer, Entity entity, float f, float g, float h, float i, CallbackInfo ci) {
        if(Client.modManager().getMod(HitboxMod.class).isEnabled()) ci.cancel();
        else return;
        float[] color = Client.modManager().getMod(HitboxMod.class).boxColor.getColor().getFloatColorWAlpha();

        Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
        WorldRenderer.drawBox(PoseStack, vertexConsumer, box, color[0], color[1], color[2], color[3]);
        if (entity instanceof EnderDragonEntity) {
            double d = -MathHelper.lerp((double) f, entity.lastRenderX, entity.getX());
            double e = -MathHelper.lerp((double) f, entity.lastRenderY, entity.getY());
            double j = -MathHelper.lerp((double) f, entity.lastRenderZ, entity.getZ());
            EnderDragonPart[] var11 = ((EnderDragonEntity)entity).getBodyParts();
            int var12 = var11.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                EnderDragonPart enderDragonPart = var11[var13];
                PoseStack.push();
                double k = d + MathHelper.lerp((double)f, enderDragonPart.lastRenderX, enderDragonPart.getX());
                double l = e + MathHelper.lerp((double)f, enderDragonPart.lastRenderY, enderDragonPart.getY());
                double m = j + MathHelper.lerp((double)f, enderDragonPart.lastRenderZ, enderDragonPart.getZ());
                PoseStack.translate(k, l, m);
                WorldRenderer.drawBox(PoseStack, vertexConsumer, enderDragonPart.getBoundingBox().offset(-enderDragonPart.getX(), -enderDragonPart.getY(), -enderDragonPart.getZ()), color[0], color[1], color[2], color[3]);
                PoseStack.pop();
            }
        }

        //Vec3d vec3d = entity.getRotationVec(tickDelta);
        //Matrix4f matrix4f = PoseStack.peek().getPositionMatrix();
        //Matrix3f matrix3f = PoseStack.peek().getNormalMatrix();
        //vertexConsumer.vertex(matrix4f, 0.0F, entity.getStandingEyeHeight(), 0.0F).color(0, 0, 255, 255).normal(matrix3f, (float)vec3d.x, (float)vec3d.y, (float)vec3d.z).next();
        //vertexConsumer.vertex(matrix4f, (float)(vec3d.x * 2.0), (float)((double)entity.getStandingEyeHeight() + vec3d.y * 2.0), (float)(vec3d.z * 2.0)).color(0, 0, 255, 255).normal(matrix3f, (float)vec3d.x, (float)vec3d.y, (float)vec3d.z).next();
    }

    @Inject(at = @At("HEAD"), method = "render")
    public <E extends Entity> void render(E entity, double x, double y, double z, float yaw, float tickDelta, PoseStack PoseStack, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        this.setRenderHitboxes(Client.modManager().isModEnabled(HitboxMod.class));
    }
}
