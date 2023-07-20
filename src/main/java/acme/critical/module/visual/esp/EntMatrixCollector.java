package acme.critical.module.visual.esp;

import java.util.HashMap;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventWorldRender;

import com.google.gson.Gson;

public class EntMatrixCollector {
    // EntityRendererMixin
    public static HashMap<Entity, MatrixStack> list = new HashMap<>();

    public static Gson die = new Gson();
}
