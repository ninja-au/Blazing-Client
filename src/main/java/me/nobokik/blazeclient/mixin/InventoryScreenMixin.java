package me.nobokik.blazeclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.nobokik.blazeclient.Client;
import me.nobokik.blazeclient.mod.GeneralSettings;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screens.recipebook.RecipeBookWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.nobokik.blazeclient.Client.mc;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Component Component) {
        super(screenHandler, playerInventory, Component);
    }


    @Shadow @Final
    private RecipeBookWidget recipeBook;
    @Shadow
    private boolean narrow;
    @Shadow
    private float mouseX;
    @Shadow
    private float mouseY;
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphicsExtractor GuiGraphicsExtractor, int i, int j, float f, CallbackInfo ci) {
        ci.cancel();
        if (this.recipeBook.isOpen() && this.narrow) {
            this.renderBackground(GuiGraphicsExtractor, i, j, f);
            this.recipeBook.render(GuiGraphicsExtractor, i, j, f);
        } else {
            super.render(GuiGraphicsExtractor, i, j, f);
            this.recipeBook.render(GuiGraphicsExtractor, i, j, f);
            this.recipeBook.drawGhostSlots(GuiGraphicsExtractor, this.x, this.y, false, f);
        }

        GuiGraphicsExtractor.drawTexture(Identifier.of("blaze-client", "blazetext.png"), 0, mc.getWindow().getScaledHeight() - 32, 0, 0, 167, 28, 167, 28);

        this.drawMouseoverTooltip(GuiGraphicsExtractor, i, j);
        this.recipeBook.drawTooltip(GuiGraphicsExtractor, this.x, this.y, i, j);
        this.mouseX = (float)i;
        this.mouseY = (float)j;
    }

}
