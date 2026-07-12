package me.nobokik.blazeclient.api.hook;


import me.nobokik.blazeclient.api.util.TextUtil;
import net.minecraft.Component.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Formatting;

import java.util.regex.Pattern;

/**
 * A chat message received by the client.
 * The Component isn't stored here for memory reasons, that's stored in the HashMap linking the message to this.
 */
public class ChatMessage {
    /**
     * A regex pattern to match " (X)", X being any number.
     * It also ensures that the closing parentheses is the end of the string.
     */
    private static final Pattern OCCURRENCES_TEXT_PATTERN = Pattern.compile("\\([0-9]+\\)$");

    /**
     * The styling for the occurrences Component
     */
    private static final Style OCCURENCES_TEXT_STYLE = Style.EMPTY.withColor(Formatting.GRAY);

    /**
     * The amount of times this message has occurred.
     */
    private int occurrences = 1;

    /**
     * Increments the occurrences counter.
     */
    public void addOccurrence() {
        occurrences++;
    }

    /**
     * Returns modifiedText appended with the amount of occurences.
     */
    public Component modifiedText(Component unmodifiedText) {
        if (occurrences == 1) {
            return unmodifiedText;
        }

        var occurrencesText = Component
                .literal(" (" + occurrences + ")")
                .setStyle(OCCURENCES_TEXT_STYLE);

        return unmodifiedText.copy().append(occurrencesText);
    }

    public Component removeTextModifications(Component modifiedText) {
        return this.removeOccurencesText(TextUtil.removeTimestamps(modifiedText));
    }

    /**
     * Returns an unmodified version of a modified Component (one that has occurrences appended).
     */
    private Component removeOccurencesText(Component modifiedText) {
        return TextUtil.removeSiblings(modifiedText, this::hasOccurrencesAppended);
    }

    /**
     * If a Component is modified with occurences or not
     */
    private boolean hasOccurrencesAppended(Component Component) {
        var hasOccurrencesAtTheEnd = OCCURRENCES_TEXT_PATTERN.matcher(Component.getString()).find();
        var hasOccurrencesStyle = Component.getStyle() == OCCURENCES_TEXT_STYLE;

        return hasOccurrencesAtTheEnd && hasOccurrencesStyle;
    }
}