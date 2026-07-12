package club.minnced.discord.rpc;

public class DiscordRPC {
    public static final DiscordRPC INSTANCE = new DiscordRPC();

    public void Discord_Initialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister, String steamId) {
        // No-op stub: Discord RPC is disabled in this build.
    }

    public void Discord_RunCallbacks() {
        // No-op stub.
    }

    public void Discord_UpdatePresence(DiscordRichPresence presence) {
        // No-op stub.
    }

    public void Discord_Shutdown() {
        // No-op stub.
    }
}
