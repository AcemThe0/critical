package acme.critical;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;

import acme.critical.event.eventbus.CriticalEventBus;
import acme.critical.event.eventbus.InexactEventHandler;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.module.client.Clickgui;
import acme.critical.profile.Profile;
import acme.critical.ui.screens.clickgui.ClickGUI;
import acme.critical.utils.FileUtils;
import acme.critical.utils.ChatUtils;


public class Critical implements ModInitializer {
    public static final Critical INSTANCE = new Critical();

    public static CriticalEventBus eventBus;
    public static final org.apache.logging.log4j.Logger EventLogger = LogManager.getFormatterLogger("critical");
    public static final Logger logger = LoggerFactory.getLogger("critical");
    private MinecraftClient mc = MinecraftClient.getInstance();

    public Path dotMc;
    public Path cjwDir;
    public Path cjwProfileDir;

    //No longer vcraft approved :(
    @Override
    public void onInitialize() {
        eventBus = new CriticalEventBus(new InexactEventHandler("critical"), Critical.EventLogger);
        logger.info("...");

        dotMc = mc.runDirectory.toPath().normalize();
        cjwDir = dotMc.resolve("Critical");
        cjwProfileDir = cjwDir.resolve("profile");

        FileUtils.mkdir(cjwDir);
        FileUtils.mkdir(cjwProfileDir);

	new Profile("main", cjwProfileDir.resolve("main"));
    }

    List<Mod> enabledModules = new ArrayList<>();

    public void onKeyPress(int key, int action) {
        if (action == GLFW.GLFW_PRESS && (mc.currentScreen == null || mc.currentScreen instanceof TitleScreen)) {
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
