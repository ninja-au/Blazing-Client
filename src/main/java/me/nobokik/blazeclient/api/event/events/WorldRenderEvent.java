package me.nobokik.blazeclient.api.event.events;

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;

@SuppressWarnings("all")
public class WorldRenderEvent {

	private static final WorldRenderEvent INSTANCE = new WorldRenderEvent();

	public LevelRenderContext context;

	public static WorldRenderEvent get(LevelRenderContext context) {
		INSTANCE.context = context;
		return INSTANCE;
	}
}
