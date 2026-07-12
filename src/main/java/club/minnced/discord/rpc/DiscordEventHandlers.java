package club.minnced.discord.rpc;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DiscordEventHandlers {
    public Consumer<DiscordUser> ready;
    public BiConsumer<Integer, String> disconnected;
}
