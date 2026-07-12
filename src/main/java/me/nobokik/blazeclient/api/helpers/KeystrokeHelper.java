package me.nobokik.blazeclient.api.helpers;

import imgui.ImFont;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import me.nobokik.blazeclient.Client;
import me.nobokik.blazeclient.api.font.JColor;
import me.nobokik.blazeclient.gui.UI;
import me.nobokik.blazeclient.mod.mods.KeystrokesMod;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class KeystrokeHelper {
    private int key;
    private String display;
    private long pressTime;
    private boolean pressed;
    public static List<KeystrokeHelper> list = new ArrayList<>();
    public KeystrokeHelper(int key, String display) {
        this.key = key;
        this.display = display;
        list.add(this);
    }

    public static KeystrokeHelper getHelper(int key) {
        for(KeystrokeHelper k : list) {
            if(k.key == key) return k;
        }
        return null;
    }

    public void drawButton() {
        double percent = Math.sin((((double) Math.min(System.currentTimeMillis() - this.pressTime, Client.modManager().getMod(KeystrokesMod.class).fadeTime.getFValue()) / Client.modManager().getMod(KeystrokesMod.class).fadeTime.getFValue()) * Math.PI) / 2);
        //double percent = 1;
        if(this.pressTime == 0) percent = 1;

        float[] bgF;
        float[] textF;
        if(pressed) {
            JColor bg = UI.blendColors(Client.modManager().getMod(KeystrokesMod.class).background.getColor(), Client.modManager().getMod(KeystrokesMod.class).pressedBackground.getColor(), (float) percent);
            bgF = bg.getFloatColor();
            JColor Component = UI.blendColors(Client.modManager().getMod(KeystrokesMod.class).Component.getColor(), Client.modManager().getMod(KeystrokesMod.class).pressedText.getColor(), (float) percent);
            textF = Component.getFloatColor();
        } else {
            JColor bg = UI.blendColors(Client.modManager().getMod(KeystrokesMod.class).pressedBackground.getColor(), Client.modManager().getMod(KeystrokesMod.class).background.getColor(), (float) percent);
            bgF = bg.getFloatColor();
            JColor Component = UI.blendColors(Client.modManager().getMod(KeystrokesMod.class).pressedText.getColor(), Client.modManager().getMod(KeystrokesMod.class).Component.getColor(), (float) percent);
            textF = Component.getFloatColor();
        }

        ImVec2 pos = ImGui.getCursorPos();
        float oldScale = ImGui.getFont().getScale();
        ImFont newFont = ImGui.getFont();

        float scaleChange;

        if(!pressed) scaleChange = (float) (0.8f + percent * 0.2f);
        else scaleChange = (float) (1f - percent * 0.2f);

        if(Client.modManager().getMod(KeystrokesMod.class).scaleChange.isEnabled()) {
            if(!pressed)
                newFont.setScale(ImGui.getFont().getScale() * scaleChange);
            else
                newFont.setScale(ImGui.getFont().getScale() * scaleChange);
        }
        ImGui.pushFont(newFont);
        if(Client.modManager().getMod(KeystrokesMod.class).textShadow.isEnabled()) {
            ImGui.setCursorPos(pos.x + 32 * 0.07f, pos.y + 32 * 0.07f);
            ImGui.pushStyleColor(ImGuiCol.Component, textF[0]/2, textF[1]/2, textF[2]/2, textF[3]);
            ImGui.pushStyleColor(ImGuiCol.Button, bgF[0], bgF[1], bgF[2], 0f);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, bgF[0], bgF[1], bgF[2], 0f);
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, bgF[0], bgF[1], bgF[2], 0f);
            if (key == GLFW.GLFW_MOUSE_BUTTON_LEFT || key == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                ImGui.button(this.display, 77f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue(), 50f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue());
            } else if (key == GLFW.GLFW_KEY_SPACE) {
                ImGui.button(this.display, 158f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue(), 50f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue());
            } else {
                ImGui.button(this.display, 50f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue(), 50f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue());
            }
            ImGui.popStyleColor(4);
        }
        ImGui.setCursorPos(pos.x, pos.y);
        ImGui.pushStyleColor(ImGuiCol.Component, textF[0], textF[1], textF[2], textF[3]);
        ImGui.pushStyleColor(ImGuiCol.Button, bgF[0], bgF[1], bgF[2], bgF[3]);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, bgF[0], bgF[1], bgF[2], bgF[3]);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, bgF[0], bgF[1], bgF[2], bgF[3]);
        if (key == GLFW.GLFW_MOUSE_BUTTON_LEFT || key == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            ImGui.button(this.display, 77f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue(), 50f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue());
        } else if (key == GLFW.GLFW_KEY_SPACE) {
            ImGui.button(this.display, 158f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue(), 50f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue());
        } else {
            ImGui.button(this.display, 50f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue(), 50f * Client.modManager().getMod(KeystrokesMod.class).scale.getFValue());
        }
        ImGui.popFont();
        ImGui.getFont().setScale(oldScale);
        ImGui.popStyleColor(4);


    }
    public void drawButton1() {}

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public long getPressTime() {
        return pressTime;
    }

    public void setPressTime(long pressTime) {
        this.pressTime = pressTime;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
