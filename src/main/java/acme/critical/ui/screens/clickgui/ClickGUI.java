package acme.critical.ui.screens.clickgui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.module.Mod.Category;
import acme.critical.utils.Render2DUtils;

public class ClickGUI extends Screen {
    public static final ClickGUI INSTANCE = new ClickGUI();

    public Mod SelectedMod = null;

    private List<Window> frames;

    private float scale = 1.0f;

    private ClickGUI() {
        super(Text.literal("ClickGUI"));

        frames = new ArrayList<>();

        int offset = 10;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, offset, 15, 80, 15));
            offset += 80;
        }

        frames.add(new SearchWindow(offset, 15, 80, 15));
        offset += 80;
        frames.add(new SettingsWindow(offset, 15, 80, 15));
    }

    @Override
    public void
    render(DrawContext context, int mouseX, int mouseY, float delta) {
        scale = 1.0f /
            (MinecraftClient.getInstance().options.getGuiScale().getValue());
        scale *= acme.critical.module.client.Clickgui.getScale();

        mouseX /= scale;
        mouseY /= scale;

        if (MinecraftClient.getInstance().world == null)
            renderBackground(context);

        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1.0f);

        context.getMatrices().push();
        for (Window frame : frames) {
            context.getMatrices().translate(0.0f, 0.0f, 1.0f);
            if (frame.selected) {
                context.getMatrices().push();
                context.getMatrices().translate(0.0f, 0.0f, 10.0f);
            }
            frame.render(context, mouseX, mouseY, delta);
            frame.updatePosition(mouseX, mouseY);
            if (frame.selected)
                context.getMatrices().pop();
        }
        context.getMatrices().pop();

        super.render(context, mouseX, mouseY, delta);
        context.getMatrices().pop();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX /= scale;
        mouseY /= scale;

        for (Window frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        mouseX /= scale;
        mouseY /= scale;

        for (Window frame : frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Window frame : frames) {
            frame.keyPressed(keyCode);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
