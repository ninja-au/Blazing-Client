package me.nobokik.blazeclient.api.event.events;

import com.mojang.blaze3d.vertex.PoseStack;

@SuppressWarnings("all")
public class HudRenderEvent {

	private static final HudRenderEvent INSTANCE = new HudRenderEvent();

	public PoseStack matrices;
	public float tickDelta;

	public static HudRenderEvent get(PoseStack matrices, float tickDelta) {
		INSTANCE.matrices = matrices;
		INSTANCE.tickDelta = tickDelta;
		return INSTANCE;
	}
}
