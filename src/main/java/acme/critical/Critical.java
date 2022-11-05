package acme.critical;

import java.util.List;
import org.slf4j.Logger;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.ui.screens.clickgui.ClickGUI;

public class Critical implements ModInitializer {
    public static final Critical INSTANCE = new Critical();

    public static final Logger logger = LoggerFactory.getLogger("critical");
    private MinecraftClient mc = MinecraftClient.getInstance();
    
    //No longer vcraft approved :(
    @Override
    public void onInitialize() {
        logger.info("...");
    }
    List<Mod> enabledModules = new ArrayList<>();

    public void onKeyPress(int key, int action) {
        if (action == GLFW.GLFW_PRESS && mc.player != null && mc.currentScreen == null) {
            for (Mod module : ModMan.INSTANCE.getModules()) {
                if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) mc.setScreen(ClickGUI.INSTANCE);
                if (key == module.getKey()) module.toggle();
            }
        }
    }

    public void onTick() {
        if (mc.player != null && mc.world != null) {
            for (Mod module : ModMan.INSTANCE.getEnabledModules()) { module.onTick(); }
        }
    }

    public void onRender2D(MatrixStack matrices, float tickDelta) {
        if (mc.player != null && mc.world != null) {
            for (Mod module : ModMan.INSTANCE.getEnabledModules()) { module.onRender2D(matrices, tickDelta); }
        }
    }
}

