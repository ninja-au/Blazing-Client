package me.nobokik.blazeclient.mixin;

import me.nobokik.blazeclient.Client;
import me.nobokik.blazeclient.api.font.JColor;
import me.nobokik.blazeclient.api.helpers.IndicatorHelper;
import me.nobokik.blazeclient.mod.mods.NametagsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.VertexConsumerProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachmentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity>  {
    @Shadow @Final protected EntityRenderDispatcher dispatcher;
    @Shadow public abstract Font getTextRenderer();

    /*
    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    protected void renderLabelIfPresent(T entity, Component Component, PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        NametagsMod mod = Client.modManager().getMod(NametagsMod.class);
        if(!mod.isEnabled()) return;
        ci.cancel();

        double d = this.dispatcher.getSquaredDistanceToCamera(entity);
        if (!(d > 4096.0)) {
            boolean bl = !entity.isSneaky();
            float f = entity.getHeight() + 0.5F;
            int i = "deadmau5".equals(Component.getString()) ? -10 : 0;
            matrices.push();
            matrices.translate(0.0F, f, 0.0F);
            matrices.multiply(this.dispatcher.getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            float g = Minecraft.getInstance().options.getTextBackgroundOpacity(0.25F);
            int j = (int)(g * 255.0F) << 24;
            Font Font = this.getTextRenderer();
            float h = (float)(-Font.getWidth(Component) / 2);
            JColor empty = new JColor(0f,0f,0f, 0f);
            j = (int)(mod.opacity.getFValue() * 255.0F) << 24;
            if(mod.textShadow.isEnabled()) {
                j = (int)(mod.opacity.getFValue()/2 * 255.0F) << 24;
                Font.draw(Component, h, (float) i, 553648127, true, matrix4f, vertexConsumers, bl ? Font.TextLayerType.SEE_THROUGH : Font.TextLayerType.NORMAL, j, light);
                if (bl) {
                    Font.draw(Component, h, (float) i, -1, true, matrix4f, vertexConsumers, Font.TextLayerType.NORMAL, 0, light);
                }
                Font.draw(Component, h, (float) i, 553648127, false, matrix4f, vertexConsumers, bl ? Font.TextLayerType.SEE_THROUGH : Font.TextLayerType.NORMAL, 0, light);
                if (entity instanceof AbstractClientPlayerEntity && Component.getString().contains(entity.getName().getString()))
                    IndicatorHelper.addBadge(entity, matrices, vertexConsumers);

                if (bl) {
                    Font.draw(Component, h, (float) i, -1, false, matrix4f, vertexConsumers, Font.TextLayerType.NORMAL, 0, light);
                }
            } else {
                Font.draw(Component, h, (float) i, 553648127, false, matrix4f, vertexConsumers, bl ? Font.TextLayerType.SEE_THROUGH : Font.TextLayerType.NORMAL, j, light);
                if (entity instanceof AbstractClientPlayerEntity && Component.getString().contains(entity.getName().getString()))
                    IndicatorHelper.addBadge(entity, matrices, vertexConsumers);

                if (bl) {
                    Font.draw(Component, h, (float) i, -1, false, matrix4f, vertexConsumers, Font.TextLayerType.NORMAL, 0, light);
                }
            }

            matrices.pop();
        }
    }
    */

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    protected void renderLabelIfPresent(T entity, Component Component, PoseStack PoseStack, VertexConsumerProvider vertexConsumerProvider, int i, float f, CallbackInfo ci) {
        NametagsMod mod = Client.modManager().getMod(NametagsMod.class);
        if(!mod.isEnabled()) return;
        ci.cancel();

        double d = this.dispatcher.getSquaredDistanceToCamera(entity);
        if (!(d > 4096.0)) {
            Vec3d vec3d = entity.getAttachments().getPointNullable(EntityAttachmentType.NAME_TAG, 0, entity.getYaw(f));
            if (vec3d != null) {
                boolean bl = !entity.isSneaky();
                int j = "deadmau5".equals(Component.getString()) ? -10 : 0;
                PoseStack.push();
                PoseStack.translate(vec3d.x, vec3d.y + 0.5, vec3d.z);
                PoseStack.multiply(this.dispatcher.getRotation());
                PoseStack.scale(0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = PoseStack.peek().getPositionMatrix();
                float g = Minecraft.getInstance().options.getTextBackgroundOpacity(0.25F);
                int k = (int)(g * 255.0F) << 24;
                Font Font = this.getTextRenderer();
                float h = (float)(-Font.getWidth(Component) / 2);
                k = (int)(mod.opacity.getFValue() * 255.0F) << 24;
                if(mod.textShadow.isEnabled()) {
                    Font.draw(Component, h, (float) j, 553648127, true, matrix4f, vertexConsumerProvider, bl ? Font.TextLayerType.SEE_THROUGH : Font.TextLayerType.NORMAL, k, i);
                    if (bl) {
                        Font.draw(Component, h, (float) j, -1, true, matrix4f, vertexConsumerProvider, Font.TextLayerType.NORMAL, 0, i);
                    }
                    Font.draw(Component, h, (float) j, 553648127, false, matrix4f, vertexConsumerProvider, bl ? Font.TextLayerType.SEE_THROUGH : Font.TextLayerType.NORMAL, k, i);
                    if (bl) {
                        Font.draw(Component, h, (float) j, -1, false, matrix4f, vertexConsumerProvider, Font.TextLayerType.NORMAL, 0, i);
                    }
                } else {
                    Font.draw(Component, h, (float) j, 553648127, false, matrix4f, vertexConsumerProvider, bl ? Font.TextLayerType.SEE_THROUGH : Font.TextLayerType.NORMAL, k, i);
                    if (bl) {
                        Font.draw(Component, h, (float) j, -1, false, matrix4f, vertexConsumerProvider, Font.TextLayerType.NORMAL, 0, i);
                    }
                }

                PoseStack.pop();
            }
        }
    }

    @Inject(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/Font;draw(Lnet/minecraft/Component/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/Font$TextLayerType;II)I", ordinal = 0))
    public void addBadges(T entity, Component Component, PoseStack PoseStack, VertexConsumerProvider vertexConsumerProvider, int i, float f, CallbackInfo ci) {
        if (entity instanceof AbstractClientPlayerEntity && Component.getString().contains(entity.getName().getString()))
            IndicatorHelper.addBadge(entity, PoseStack, vertexConsumerProvider);
    }
}
