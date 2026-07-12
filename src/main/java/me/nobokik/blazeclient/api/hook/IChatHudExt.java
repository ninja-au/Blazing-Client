package me.nobokik.blazeclient.api.hook;

import net.minecraft.client.multiplayer.chat.GuiMessage.Line;

import java.util.List;

public interface IChatHudExt {
    List<ChatHudLine> compactchat$getMessages();
    void compactchat$refreshMessages();
    void compactchat$clear();
}