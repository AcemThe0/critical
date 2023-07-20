package acme.critical.event.events;

import net.minecraft.client.MinecraftClient;

import acme.critical.event.eventbus.Event;
import acme.critical.ui.screens.clickgui.ClickGUI;

public class EventTick extends Event {
    public static final short IN_WORLD = 1 << 0;
    public static final short IN_CGUI = 1 << 1;

    private static MinecraftClient mc = MinecraftClient.getInstance();
    private short context;

    public EventTick() {
        if (mc.player != null && mc.world != null)
            context |= IN_WORLD;
        if (mc.currentScreen instanceof ClickGUI)
            context |= IN_CGUI;
    }

    public short getContext() { return context; }
}
