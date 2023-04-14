package acme.critical.event.events;

import java.lang.Character;
import org.lwjgl.glfw.GLFW;

import acme.critical.event.Event;

public class EventKeyboard extends Event {
	private int key;
	private int action;
	private int modifiers;

	public EventKeyboard(int key, int action, int modifiers) {
		this.key = key;
		this.action = action;
		this.modifiers = modifiers;
	}

	public int getKey() {
		return key;
	}
	public int getAction() {
		return action;
	}
	public int getModifiers() {
		return modifiers;
	}

	public char getKeyReadable() {
		if (key == GLFW.GLFW_KEY_SPACE) return ' ';
		char c = GLFW.glfwGetKeyName(key, 0).charAt(0);
		if ((modifiers & GLFW.GLFW_MOD_SHIFT) != 0) c = Character.toUpperCase(c);

		return c;
	}

	public boolean isDel() {
		if (key == GLFW.GLFW_KEY_BACKSPACE || key == GLFW.GLFW_KEY_DELETE) return true;
		return false;
	}

	public boolean isPressing() {
		if (action == GLFW.GLFW_RELEASE) return false;
		return true;
	}

	public static class Cgui extends EventKeyboard {
		public Cgui(int key, int action, int modifiers) {
			super(key, action, modifiers);
		}
	}
}
