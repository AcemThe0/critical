package acme.critical.module.visual.esp;

import java.util.HashMap;

import com.google.gson.Gson;

import net.minecraft.entity.Entity;
import net.minecraft.client.util.math.MatrixStack;

import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventWorldRender;

public class EntMatrixCollector {
	// EntityRendererMixin
	public static HashMap<Entity, MatrixStack> list = new HashMap<>();

	public static Gson die = new Gson();
}
