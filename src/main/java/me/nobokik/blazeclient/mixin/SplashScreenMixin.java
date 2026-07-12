package me.nobokik.blazeclient.mixin;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.nobokik.blazeclient.menu.MainMenuButtons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.SplashOverlay;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resource.ResourceReload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.phys.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.*;
import java.util.Optional;
import java.util.OptionalInt;

@Mixin(SplashOverlay.class)
public abstract class SplashScreenMixin extends Overlay {
    @Shadow
    private long reloadCompleteTime;
    @Shadow @Final @Mutable
    private static int MOJANG_RED;
    @Shadow @Final @Mutable
    private static int MONOCHROME_BLACK;

    @Shadow @Final
    private Minecraft client;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(GuiGraphicsExtractor GuiGraphicsExtractor, int i, int j, float f, CallbackInfo ci) {
        //LOGO =  Identifier.of("blaze-client","icon.png");
        Identifier BG = Identifier.of("blaze-client", "waves.png");
        MOJANG_RED = argb(255, 30, 30, 46);
        MONOCHROME_BLACK = argb(255, 30, 30, 46);
        if (this.reloadCompleteTime > 1) {
            this.client.setOverlay(null);
            MainMenuButtons.reloadComplete = true;
        }
    }

    private static int argb(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
