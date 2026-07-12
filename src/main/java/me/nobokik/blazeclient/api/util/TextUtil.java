package me.nobokik.blazeclient.api.util;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Component;

import java.util.function.Predicate;

public class TextUtil {
    /**
     * A regex pattern to match a timestamp of HH:mm(:ss?).
     * It can be surrounded by any character.
     */
    private static final String TIMESTAMP_PATTERN = ".?\\d{1,2}:\\d{2}(:\\d{2})*.?";

    public static Component removeSiblings(Component parent, Predicate<Component> predicate) {
        var copy = parent.copy();
        copy.getSiblings().removeIf(predicate);

        return copy;
    }

    /**
     * Most mods add create a new Component literal and append the original Component to it.
     * This method removes the timestamp from the literal Component content.
     */
    public static Component removeTimestamps(Component component) {
        var content = component.getContent();

        var string = content.toString();
        var withoutTimestamps = string.replaceAll(TIMESTAMP_PATTERN, "");
        if (withoutTimestamps.equals(string)) {
            return component;
        }

        var newText = Component.literal(withoutTimestamps.trim());
        newText.setStyle(newText.getStyle());
        newText.getSiblings().addAll(component.getSiblings());

        return newText;
    }
}
