package acme.critical;

import org.slf4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.slf4j.LoggerFactory;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import acme.critical.ui.screens.clickgui.ClickGUI;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class Critical implements ModInitializer {
    public static String Hack = "Critical";
    public static String Version = "1.1.0-cb";

    public static final Critical INSTANCE = new Critical();
    public static final Logger logger = LoggerFactory.getLogger("Critical");
    private MinecraftClient mc = MinecraftClient.getInstance();
    
    @Override
    public void onInitialize() {
        logger.info("...");
    }

    public void onKeyPress(int key, int action) {
        if (action == GLFW.GLFW_PRESS && mc.player != null && mc.currentScreen == null) {
            for (Mod module : ModMan.INSTANCE.getModules()) {
                if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) mc.setScreen(ClickGUI.INSTANCE);
                if (key == module.getKey()) module.toggle();
            }
        }
    }

    public void onTick() {
        if (mc.player != null) {
            for (Mod module : ModMan.INSTANCE.getEnabledModules()) {
                module.onTick();
            }
        }
    }
}
