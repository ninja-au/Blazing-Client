package me.nobokik.blazeclient.mixin;

import me.nobokik.blazeclient.Client;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.network.ServerInfo;


@Mixin(value = MultiplayerServerListWidget.ServerEntry.class, priority = 0)
public class ServerEntryMixin {

    @Shadow
    private @Final ServerInfo server;
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;drawText(Lnet/minecraft/client/font/Font;Ljava/lang/String;IIIZ)I", ordinal = 0))
    public int changeText(GuiGraphicsExtractor instance, Font Font, String string, int i, int j, int k, boolean bl) {
        if(Client.starServers.contains(server.address.toLowerCase())) instance.drawText(Font, "★", i-50, j+14, 16776960, true);
        return instance.drawText(Font, string, i, j, k, bl);
    }

}
